package com.ck.wechatnotification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
  private String content;
  private String toUserName;
  private String fromUserName;
  private String createTime;
  private String msgType;
  private String msgId;
  private String agentID;
}
