package com.github.wxpay.sdk;

import java.io.InputStream;

/**
 * Created by 11153 on 2020/04/20.
 */
public class MyConfig extends WXPayConfig {

    @Override
    String getAppID() {
        return "wx8397f8696b538317";
    }

    @Override
    String getMchID() {
        return "1473426802";
    }

    @Override
    String getKey() {
        return "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }

            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo("api.mch.weixin.qq.com", true);
            }
        };
    }
}
