#!/bin/bash
#
# ====================================================== 使用说明 ======================================================
# 1. 赋予执行权限: chmod +x run.sh
# 2. 启动应用程序: sh run.sh start
# 3. 停止应用程序: sh run.sh stop
# 4. 查看应用程序: sh run.sh status
# 5. 重启应用程序: sh run.sh restart -c -d --ignore-console # -c 清空日志目录，-d 以后台进程方式启动，--ignore-console 忽略控制台输出
# 6. 查看应用日志: sh run.sh logs
# ====================================================== 作者信息 ======================================================
# @author: houyu
# @date: 2021-08-30
# @mail: for.houyu@qq.com(272694308@qq.com)
# @blog: https://www.ihouyu.cn
# @csdn: https://blog.csdn.net/JinglongSource
# ====================================================== 初始准备 =====================================================
# 刷新环境变量
export PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin
source /etc/profile
# ====================================================== 参数设置 ======================================================
# === JAVA_HOME 路径（默认使用环境变量的 JAVA_HOME）
#JAVA_HOME_PATH="/usr/local/java/jdk1.8.0_281"
JAVA_HOME_PATH="${JAVA_HOME}"
# ---------------------------------------------------------------
# === 目录路径（默认使用脚本文件所在的目录路径）
DIR_PATH=$(cd "$(dirname "$0")" && pwd)
# ---------------------------------------------------------------
# === 服务文件名（默认使用目录下最新的.jar或者.war文件）
SERVER_FILE_NAME=$(ls -t "${DIR_PATH}" | grep -E '\.jar$|\.war$' | grep -v "_execution." | head -1)
# ---------------------------------------------------------------
# === Java 参数
JAVA_OPT="${JAVA_OPT} -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8"
JAVA_OPT="${JAVA_OPT} -Xms128m -Xmx128m -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=128M -Xss512k"
JAVA_OPT="${JAVA_OPT} -XX:+UseParNewGC -XX:+UseConcMarkSweepGC"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError"
JAVA_OPT="${JAVA_OPT} -XX:HeapDumpPath=${DIR_PATH}/logs/heap_dump.hprof"
JAVA_OPT="${JAVA_OPT} -Xloggc:${DIR_PATH}/logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC"
JAVA_OPT="${JAVA_OPT} -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
# ---------------------------------------------------------------
# === Spring 参数
SPRING_OPT="${SPRING_OPT} --spring.profiles.active=prod"
# ---------------------------------------------------------------
# === 停止时等待超时的秒数
STOP_TIMEOUT=60
# ====================================================== 方法定义 ======================================================
# 控制台文件
CONSOLE_FILE="${DIR_PATH}/logs/console.log"
# 服务PID
SERVER_PID=0
# 服务端口
SERVER_PORT=0
# 输入的所有参数
INPUT_PARAMETER=$*
# =======================================
# 解析服务的 pid
# =======================================
resolve_server_pid() {
    SERVER_PID=0
    if [ -f "${DIR_PATH}/logs/server.pid" ]; then
        # pid 文件存在则读取文件获取pid
        SERVER_PID=$(cat "${DIR_PATH}/logs/server.pid")
        if [[ $(ps -ef | awk '{print $2}' | grep -w -c "${SERVER_PID}") -eq 0 ]]; then
            SERVER_PID=0
            rm -rf "${DIR_PATH}/logs/server.pid"
            sleep 0.1
        fi
    #else
    #    # pid 文件不存在则尝试根据路径下的应用名称获取pid
    #    if [[ $(pgrep -f -c "java.*${DIR_PATH}/${SERVER_FILE_NAME}") -gt 0 ]]; then
    #        SERVER_PID=$(pgrep -f "java.*${DIR_PATH}/${SERVER_FILE_NAME}" | head -1)
    #    fi
    fi
}
# =======================================
# 解析服务的 port
# =======================================
retry_resolve_server_port() {
    SERVER_PORT=0
    # 检测5分钟(300秒)
    for(( i=0; i<=300; i++ )); do
        # 判断 pid 是否存在
        resolve_server_pid
        if test "${SERVER_PID}" -eq 0; then
            break
        fi
        # 判断 pid 的 port 是否绑定
        if test "$(netstat -tlnp | grep -c -E "\s+${SERVER_PID}/")" -gt 0; then
            SERVER_PORT=$(netstat -tlnp | grep -E "\s+${SERVER_PID}/" | head -1 | awk '{print $4}' | awk -F ":" '{print $NF}')
            break
        fi
        [[ $i%5 -eq 0 ]] && echo "--- Observing server port using ${i}s"
        sleep 1
    done
}
# =======================================
# 启动
# =======================================
start() {
    echo "----------------------------------------------------------------------------- Start  [0] ------"
    # 检查服务状态
    resolve_server_pid
    if [[ ${SERVER_PID} -ne 0 ]]; then
        echo "--- Do not start. ${SERVER_FILE_NAME} already started! [ SERVER_PID = ${SERVER_PID} ]"
        echo "----------------------------------------------------------------------------- Start  [1] ------"
        return
    fi
    # 检查 SERVER_FILE_NAME 是否存在
    if [ ! -e "${DIR_PATH}/${SERVER_FILE_NAME}" ]; then
        echo -e "\033[31mPlease set a valid variable SERVER_FILE_NAME!\033[0m"
        exit 1
    fi
    # 检查 SERVER_FILE_NAME 是否需要复制
    if [[ $(echo "${SERVER_FILE_NAME}" | grep -c "_execution.") -eq 0 ]]; then
        _EXECUTION_SERVER_FILE_NAME=$(echo "${SERVER_FILE_NAME}" | sed 's/\(.*\)\.\(.*\)/\1_execution.\2/g')
        # 强制复制一份文件用于运行
        echo -e "--- \033[33mForce copy file to running, execution file is ${_EXECUTION_SERVER_FILE_NAME}\033[0m"
        \cp "${DIR_PATH}/${SERVER_FILE_NAME}" "${DIR_PATH}/${_EXECUTION_SERVER_FILE_NAME}"
        SERVER_FILE_NAME="${_EXECUTION_SERVER_FILE_NAME}"
    fi
    # 检查 JAVA_HOME_PATH
    if [ ! -d "${JAVA_HOME_PATH}" ]; then
        echo -e "\033[31mPlease set a valid variable JAVA_HOME_PATH!\033[0m"
        exit 1
    fi
    # 检查日志目录
    if [[ $(echo "$INPUT_PARAMETER" | grep -c -E "\s+-[A-Za-z]*c") -gt 0 ]]; then
        # 存在 -c clear 则删除日志目录
        echo -e "--- \033[33mDeleting ${SERVER_FILE_NAME} file directory(${DIR_PATH}/logs)\033[0m"
        rm -rf "${DIR_PATH}/logs"
    fi
    test -d "${DIR_PATH}/logs" || mkdir -p "${DIR_PATH}/logs"
    #
    echo "--- Starting ${SERVER_FILE_NAME} ..."
    # 执行脚本
    # nohup java -Xmx128m -jar /app/server.jar --spring.profiles.active=prod >> /app/logs/console.log 2>&1 & echo $! > /app/logs/server.pid
    # 封装运行参数
    _RUN_OPT="${JAVA_HOME_PATH}/bin/java ${JAVA_OPT} -jar ${DIR_PATH}/${SERVER_FILE_NAME}"
    [ -n "${SPRING_OPT}" ] && _RUN_OPT="${_RUN_OPT} ${SPRING_OPT}"
    echo "--- ${_RUN_OPT}"
    echo "${_RUN_OPT}" > "${CONSOLE_FILE}"
    # 处理服务控制台文件
    _SERVER_CONSOLE_FILE="${CONSOLE_FILE}"
    if [[ $(echo "$INPUT_PARAMETER" | grep -c -E "\s+--ignore-console") -gt 0 ]]; then
        # 存在 -ignore-console 则忽略服务输出的日志
        echo "--ignore-console" >> "${CONSOLE_FILE}"
        _SERVER_CONSOLE_FILE="/dev/null"
    fi
    #
    _PREV_PATH=$(pwd)
    cd "${DIR_PATH}"
    nohup ${_RUN_OPT} >> "${_SERVER_CONSOLE_FILE}" 2>&1 & echo $! > "${DIR_PATH}/logs/server.pid"
    cd "${_PREV_PATH}"
    sleep 1
    #
    echo "--- Running ${SERVER_FILE_NAME} ..."
    echo -e "--- You can check the log file \033[36m${CONSOLE_FILE}\033[0m or execute the command on the next line"
    echo -e "--- \033[40;37mtail -f -n 200 ${CONSOLE_FILE}\033[0m"
    # 判断运行是否成功
    resolve_server_pid
    if [[ ${SERVER_PID} -eq 0 ]]; then
        echo -e "--- \033[31mStart failure ${SERVER_FILE_NAME}\033[0m"
    else
        if [[ $(echo "$INPUT_PARAMETER" | grep -c -E "\s+-[A-Za-z]*d") -gt 0 ]]; then
            # 存在 -d Daemon 以后台进程方式启动
            echo -e "--- \033[32mStart successfully ${SERVER_FILE_NAME} [ SERVER_PID = ${SERVER_PID}, \033[0m\033[33mSERVER_PORT = unknown \033[0m\033[32m]\033[0m"
        else
            retry_resolve_server_port
            if [[ ${SERVER_PORT} -eq 0 ]]; then
                echo -e "--- \033[31mStart failure ${SERVER_FILE_NAME}\033[0m"
            else
                _SERVER_PORT_TEXT=$(netstat -tulnp | grep -E "\s+${SERVER_PID}/" | awk 'BEGIN{ORS=" "}{print $1"-"$4}')
                echo -e "--- \033[32mStart successfully ${SERVER_FILE_NAME} [ SERVER_PID = ${SERVER_PID}, SERVER_PORT = ${_SERVER_PORT_TEXT}]\033[0m"
            fi
        fi
    fi
    echo "----------------------------------------------------------------------------- Start  [1] ------"
}
# =======================================
# 停止
# =======================================
stop() {
    echo "----------------------------------------------------------------------------- Stop   [0] ------"
    #
    resolve_server_pid
    if [[ ${SERVER_PID} -eq 0 ]]; then
        echo "--- Stopped ${SERVER_FILE_NAME}"
        echo "----------------------------------------------------------------------------- Stop   [1] ------"
        return
    fi
    echo "--- Graceful Stopping ${SERVER_FILE_NAME} ..."
    # 进行优雅停止
    kill -15 ${SERVER_PID}
    _WAIT_SECONDS=0
    while true; do
        resolve_server_pid
        if [[ ${SERVER_PID} -eq 0 ]]; then
            echo "--- Stopped ${SERVER_FILE_NAME}"
            break
        fi
        if [[ ${_WAIT_SECONDS} -ge ${STOP_TIMEOUT} ]]; then
            # 强制终止进程
            echo -e "--- \033[31mWait timeout(${_WAIT_SECONDS}s), forced shutdown [kill -9 ${SERVER_FILE_NAME}].\033[0m"
            sudo kill -9 ${SERVER_PID}
            break
        fi
        sleep 1
        ((_WAIT_SECONDS++))
        echo "--- Wait ${_WAIT_SECONDS}s"
    done
    if [[ $? -eq 0 ]]; then
        echo "--- Stop successfully ${SERVER_FILE_NAME}"
        rm -rf "${DIR_PATH}/logs/server.pid"
    else
        echo "--- Stop failure ${SERVER_FILE_NAME}"
    fi
    echo "----------------------------------------------------------------------------- Stop   [1] ------"
}
# =======================================
# 状态
# =======================================
status() {
    echo "----------------------------------------------------------------------------- status [0] ------"
    #
    resolve_server_pid
    if [[ ${SERVER_PID} -eq 0 ]]; then
        echo -e "--- \033[33mStopped $SERVER_FILE_NAME\033[0m"
    else
        _SERVER_PORT_TEXT=$(netstat -tulnp | grep -E "\s+${SERVER_PID}/" | awk 'BEGIN{ORS=" "}{print $1"-"$4}')
        [ -z "$_SERVER_PORT_TEXT" ] && _SERVER_PORT_TEXT="unknown "
        echo -e "--- \033[32mRunning ${SERVER_FILE_NAME} [ SERVER_PID = ${SERVER_PID}, SERVER_PORT = ${_SERVER_PORT_TEXT}]\033[0m"
        echo -e "--- You can check the log file \033[36m${CONSOLE_FILE}\033[0m or execute the command on the next line"
        echo -e "--- \033[40;37mtail -f -n 200 ${CONSOLE_FILE}\033[0m"
    fi
    echo "----------------------------------------------------------------------------- status [1] ------"
}
# =======================================
# 查看日志
# =======================================
logs() {
    tail -f -n 200 "${CONSOLE_FILE}"
}
# =======================================
# 切割日志
# =======================================
split_logs() {
    echo "----------------------------------------------------------------------------- split  [0] ------"
    #
    if [[ $(find "${CONSOLE_FILE}" -size +20M | wc -l) -gt 0 ]]; then
        # 大于20M
        _DATE_TIME=$(date +%Y%m%d_%H%M%S)
        # split -b 10m -d large_file.log new_file_prefix
        # -b 以字节分割; -d 序号用数字
        split -b 20m -d "${CONSOLE_FILE}" "${CONSOLE_FILE}_${_DATE_TIME}_"
        # 清空文件
        cat /dev/null > "${CONSOLE_FILE}"
        # 删除多余的日志, 只保留最后的20份
        find "${DIR_PATH}/logs" -name "$(basename "${CONSOLE_FILE}")_*" | sort | head -n -20 | xargs -i rm -rf {}
        #
        echo "--- Split finished ${SERVER_FILE_NAME} ${CONSOLE_FILE}"
    else
        echo "--- Do not split ${SERVER_FILE_NAME} ${CONSOLE_FILE}"
    fi
    echo "----------------------------------------------------------------------------- split  [1] ------"
}
# ====================================================== 入口分支 ======================================================
#
case "$1" in
'start')
    start
    ;;
'stop')
    stop
    ;;
'restart')
    stop
    start
    ;;
'status')
    status
    ;;
'logs')
    logs
    ;;
'split')
    split_logs
    ;;
*)
    echo "requires parameter [start|stop|restart|status|logs|split] [-c] [-d] [--ignore-console]"
    exit 1
    ;;
esac
#
#