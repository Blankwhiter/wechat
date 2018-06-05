package com.example.demo.vo;

import lombok.Data;

/**
 * 语音消息
 */
@Data
public class VoiceMessage {
    // 媒体ID
    private String MediaId;
    // 语音格式
    private String Format;
}
