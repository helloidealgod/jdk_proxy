package com.example.servlet;

import java.util.Map;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/30 13:54
 */
public class HttpRequest {
    private String method;
    private String url;
    private String version;
    private Map<String, String> header;
    private byte[] body;
    private String data;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
