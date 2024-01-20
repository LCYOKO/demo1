package com.xiaomi.demo.net;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
@Slf4j
public abstract class AbstractRequestHandler<T> {
    protected Request request;
    protected Response response;
    protected T socketWrapper;
    protected boolean isFinished;


    public AbstractRequestHandler(T socketWrapper, Request request, Response response) {
        this.socketWrapper = socketWrapper;
        this.isFinished = false;
        this.request = request;
        this.response = response;
    }

    /**
     * 调用servlet
     */
    public abstract void service();
}
