package com.ck.wechatnotification.controller;

import com.alibaba.fastjson.JSONObject;
import com.ck.wechatnotification.converter.MessageConverter;
import com.ck.wechatnotification.dto.NotificationDTO;
import com.ck.wechatnotification.mqtt.MqttGateway;
import com.ck.wechatnotification.qq.weixin.mp.aes.AesException;
import com.ck.wechatnotification.qq.weixin.mp.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class NotificationController {

  @Autowired private WXBizMsgCrypt wxcpt;
  @Autowired private MessageConverter messageConverter;
  @Autowired MqttGateway mqttGateway;

  @RequestMapping("/test")
  public String test() {
    return "success";
  }

  @GetMapping("/callback")
  public String checkURL(String msg_signature, String timestamp, String nonce, String echostr)
      throws AesException {
    String echoStr = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
    log.info("verifyURL echo str: " + echoStr);
    return echoStr;
  }

  @PostMapping("/callback")
  public void wxPush(
      String msg_signature, String timestamp, String nonce, @RequestBody String postData) {
    try {
      String msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);
      log.info("after decrypt msg: " + msg);
      NotificationDTO notificationDTO = messageConverter.fromRawMsg(msg);
      log.info("notification: " + JSONObject.toJSONString(notificationDTO));
      mqttGateway.sendToMqtt(JSONObject.toJSONString(notificationDTO));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
