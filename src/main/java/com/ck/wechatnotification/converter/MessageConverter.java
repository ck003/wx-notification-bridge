package com.ck.wechatnotification.converter;

import com.ck.wechatnotification.dto.NotificationDTO;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Component
public class MessageConverter {

  public NotificationDTO fromRawMsg(String msg)
      throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    StringReader sr = new StringReader(msg);
    InputSource is = new InputSource(sr);
    Document document = db.parse(is);
    Element root = document.getDocumentElement();
    NotificationDTO notificationDTO = new NotificationDTO();
    notificationDTO.setContent(root.getElementsByTagName("Content").item(0).getTextContent());
    notificationDTO.setToUserName(
        root.getElementsByTagName("FromUserName").item(0).getTextContent());
    notificationDTO.setFromUserName(
        root.getElementsByTagName("FromUserName").item(0).getTextContent());
    notificationDTO.setCreateTime(root.getElementsByTagName("CreateTime").item(0).getTextContent());
    notificationDTO.setMsgType(root.getElementsByTagName("MsgType").item(0).getTextContent());
    notificationDTO.setMsgId(root.getElementsByTagName("MsgId").item(0).getTextContent());
    notificationDTO.setAgentID(root.getElementsByTagName("AgentID").item(0).getTextContent());
    return notificationDTO;
  }
}
