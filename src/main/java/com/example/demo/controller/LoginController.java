package com.example.demo.controller;

import com.example.demo.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 签名验证
 */
@RestController
@Slf4j
public class LoginController {

    /**
     * 与微信服务器确认连接
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     */
    @GetMapping(value = "")
    public String checkWeChatSign(@RequestParam("signature") String signature,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce,
                                @RequestParam("echostr") String echostr ){
        if (SignUtil.checkSignature(signature,timestamp,nonce)) {
            log.info("[***微信服务器验证成功***] ： "+echostr);
        }else {
            log.info("[***微信服务器验证失败***]");
            echostr ="";
        }
        return echostr;
    }

}
