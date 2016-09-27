package com.shj.commonview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHJ on 2016/9/25.
 */

public class Message {
    private String content;
    private boolean isTitle;
    public Message(String content,boolean isComMsg){
        this.content = content;
        this.isTitle = isComMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    @Override
    public String toString() {
        return "MessageContent:"+content;
    }
    public static List<Message> MOCK_DATAS = new ArrayList<>();
    static {
        Message msg = null;
        msg = new Message("最新故事",true);
        MOCK_DATAS.add(msg);
        msg = new Message("故事一",false);
        MOCK_DATAS.add(msg);
        msg = new Message("故事二",false);
        MOCK_DATAS.add(msg);
        msg = new Message("故事三",false);
        MOCK_DATAS.add(msg);
        msg = new Message("最新资源",true);
        MOCK_DATAS.add(msg);
        msg = new Message("资源一",false);
        MOCK_DATAS.add(msg);
        msg = new Message("资源二",false);
        MOCK_DATAS.add(msg);
        msg = new Message("最新文章",true);
        MOCK_DATAS.add(msg);
        msg = new Message("文章一",false);
        MOCK_DATAS.add(msg);
        msg = new Message("文章二",false);
        MOCK_DATAS.add(msg);
        msg = new Message("文章三",false);
        MOCK_DATAS.add(msg);
        msg = new Message("文章四",false);
        MOCK_DATAS.add(msg);
    }
}
