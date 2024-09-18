package com.xiaomi.demo.flow.flowable;


import org.flowable.engine.common.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.common.api.delegate.event.FlowableEvent;
import org.flowable.engine.common.api.delegate.event.FlowableEventListener;
import org.flowable.engine.common.api.delegate.event.FlowableEventType;

/**
 * @Author: liuchiyun
 * @Date: 2024/9/18
 */
public class MyEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {
        FlowableEventType type = event.getType();
        if (FlowableEngineEventType.JOB_EXECUTION_SUCCESS.equals(type)) {
            System.out.println("A job well done!");
        } else if (FlowableEngineEventType.JOB_EXECUTION_FAILURE.equals(type)) {
            System.out.println("A job has failed...");
        } else {
            System.out.println("Event received: " + event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        // onEvent方法中的逻辑并不重要，可以忽略日志失败异常……

        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
