package com.hero.mvcdemo.base;

import android.os.Bundle;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * EventBus 发送事件基类
 */
public class BaseEventType {

    private String sender = "all";      //发送者
    private String recipient = "all";   //接收者
    private Bundle bundle;
    private String mMsg;
    private int code;
    private int hashcode = -1;
    private JSONObject response;
    private HashMap<Integer , Integer> hashMap;
    public BaseEventType(String msg) {
        mMsg = msg;
    }

    public BaseEventType(int code, String msg) {
        this.code = code;
        mMsg = msg;
    }
    public BaseEventType(int code) {
        this.code = code;
    }
    public BaseEventType(Bundle bundle) {
        this.bundle = bundle;
    }

    public BaseEventType(JSONObject response){
        this.response=response;
    }

    public BaseEventType(int hashcode, HashMap<Integer , Integer> hashMap){
        this.hashcode = hashcode;
        this.hashMap = hashMap;
    }

    public BaseEventType(String sender, String recipient, String msg) {
        this.sender = sender;
        this.recipient = recipient;
        this.mMsg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMsg() {
        return mMsg;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }
    public int getHashcode() {
        return hashcode;
    }

    public void setHashcode(int hashcode) {
        this.hashcode = hashcode;
    }
    public HashMap<Integer, Integer> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<Integer, Integer> hashMap) {
        this.hashMap = hashMap;
    }
}
