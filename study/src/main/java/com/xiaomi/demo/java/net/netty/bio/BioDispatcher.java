package com.xiaomi.demo.java.net.netty.bio;

import com.xiaomi.demo.java.net.AbstractDispatcher;
import com.xiaomi.demo.java.net.Request;
import com.xiaomi.demo.java.net.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/20
 */
@Slf4j
public class BioDispatcher extends AbstractDispatcher<BioSocketWrapper> {
    @Override
    public void doDispatch(BioSocketWrapper socketWrapper) {
        try {
            BufferedInputStream bin = new BufferedInputStream(socketWrapper.getSocket().getInputStream());
            byte[] buf;
            buf = new byte[bin.available()];
            int len = bin.read(buf);
            if (len <= 0) {
                closeMute(socketWrapper);
                return;
            }
            Request request = new Request(buf);
            Response response = new Response();
            threadPoolExecutor.execute(new BioRequestHandler(socketWrapper, request, response));
        } catch (Exception ex) {
            log.error("dispatcher socket:{} error", socketWrapper, ex);
            closeMute(socketWrapper);
        }
    }

    private void closeMute(BioSocketWrapper bioSocketWrapper) {
        try {
            bioSocketWrapper.close();
        } catch (Exception ex) {
            log.error("close socket:{} failed", bioSocketWrapper, ex);
        }
    }
}
