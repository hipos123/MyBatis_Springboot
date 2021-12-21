package com.yaoxj.entity;

import org.springframework.data.redis.listener.Topic;

import java.io.Serializable;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-24 11:33
 **/
public class Content implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;
    private String Topic;
    private int numPage;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public int getNumPage() {
        return numPage;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }
}
