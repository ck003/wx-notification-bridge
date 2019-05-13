package com.ck.wechatnotification.config;

import com.ck.wechatnotification.qq.weixin.mp.aes.AesException;
import com.ck.wechatnotification.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WXBizMsgCryptConfig {

  @Value("${wechat.token}")
  private String token;

  @Value("${wechat.encodingAESKey}")
  private String encodingAESKey;

  @Value("${wechat.corpId}")
  private String corpId;

  @Bean
  public WXBizMsgCrypt wxcpt() throws AesException {
    return new WXBizMsgCrypt(token, encodingAESKey, corpId);
  }
}
