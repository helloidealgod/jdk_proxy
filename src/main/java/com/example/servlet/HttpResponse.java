package com.example.servlet;

import java.util.Map;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/30 13:55
 */
public class HttpResponse {
    private String status;
    private Map<String, String> header;
    private byte[] body;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
