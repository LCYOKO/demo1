package com.xiaomi.auth.oauth2;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import org.springframework.stereotype.Service;

/**
 * @Author liuchiyun
 * @Date 2024/8/6
 */
@Service
public class OAuth2TemplateImpl extends SaOAuth2Template {

    /**
     * 根据 id 获取 Client 信息
     */
    @Override
    public SaClientModel getClientModel(String clientId) {
        // 此为模拟数据，真实环境需要从数据库查询
        if ("1001".equals(clientId)) {
            return new SaClientModel()
                    .setClientId("1001")
                    .setClientSecret("aaaa-bbbb-cccc-dddd-eeee")
                    .setAllowUrl("*")
                    .setContractScope("userinfo")
                    .setIsAutoMode(true);
        }
        return null;
    }

    /**
     * 根据ClientId 和 LoginId 获取openid
     */
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此为模拟数据，真实环境需要从数据库查询
        return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
    }
}
