package com.example.demo.controller;

import com.example.demo.constant.MessageType;
import com.example.demo.utils.MessageUtil;
import com.example.demo.utils.XmlUtil;
import com.example.demo.vo.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;


/**
 *消息回应控制类
 */
@RestController
@Slf4j
public class MessageController {

    @PostMapping("")
    public String dealMessages(HttpServletRequest request) throws Exception {
        //对客户请求的xml 进行解析
        Map<String, String> requestMap = XmlUtil.parseXml(request);
        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        log.info("[*** FromUserName ***] : " + fromUserName);
        log.info("[*** toUserName ***] : " + toUserName);
        log.info("[*** msgType ***] : " + msgType);
        //消息内容
        String content = requestMap.get("Content");
        //文本类型验证
        if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_TEXT.value)) {
            //如果用户发送表情，则回复同样表情。
            if (MessageUtil.isQqFace(content)) {
                return MessageUtil.initTextMessage(toUserName, fromUserName, content);
            } else {
                if ("文本".equals(content)) {
                    return MessageUtil.initTextMessage(toUserName, fromUserName, "文本");
                } else if ("图文".equals(content)) {
                    Article article = new Article();
                    article.setTitle("只有一条图文");
                    article.setDescription("这是我具体的描述");
                    article.setPicUrl("http://img2.imgtn.bdimg.com/it/u=2643232352,3928970336&fm=214&gp=0.jpg");
                    article.setUrl("https://blog.csdn.net/belonghuang157405");
                    ArrayList<Article> articles = new ArrayList<>();
                    articles.add(article);
                    return MessageUtil.initNewsMessage(toUserName, fromUserName, articles);
                } else if ("多图文".equals(content)) {
                    ArrayList<Article> articles = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        Article article = new Article();
                        article.setTitle("一条图文" + i);
                        article.setDescription("这是我具体的描述" + i);
                        article.setPicUrl("http://img2.imgtn.bdimg.com/it/u=2643232352,3928970336&fm=214&gp=0.jpg");
                        article.setUrl("https://blog.csdn.net/belonghuang157405");
                        articles.add(article);
                    }
                    return MessageUtil.initNewsMessage(toUserName, fromUserName, articles);
                } else if ("网址".equals(content)) {
                    return MessageUtil.initTextMessage(toUserName, fromUserName, "<a href='https://blog.csdn.net/belonghuang157405'>个人博客</a>");
                }
            }
        } else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_IMAGE.value)) {//图片
            String picUrl = requestMap.get("PicUrl");
            return MessageUtil.initTextMessage(toUserName, fromUserName, picUrl);
        } else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_LINK.value)) {//链接
            String title = requestMap.get("Title");
            String description = requestMap.get("Description");
            String url = requestMap.get("Url");
            return MessageUtil.initTextMessage(toUserName, fromUserName, description);
        } else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_LOCATION.value)) {//地理位置
            String label = requestMap.get("Label");
            String location_x = requestMap.get("Location_X");
            String location_y = requestMap.get("Location_Y");
            return MessageUtil.initTextMessage(toUserName, fromUserName, label);
        } else if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_VOICE.value)) {//声音
            return MessageUtil.initTextMessage(toUserName, fromUserName, "你发送了声音");
        }

        log.info("[*** 无法匹配到相应的类型 ***]");
        return "";
    }
}
