package org.jeecg.modules.cg.cg_kafka.utils;


public class ReplyBean {

    public int code;
    public String message;
    public Long latency;

    public ReplyBean() {
    }

    public ReplyBean(int code, String message, Long latency) {
        this.code = code;
        this.message = message;
        this.latency = latency;
    }

    @Override
    public String toString() {
        return "ReplyBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", latency=" + latency +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLatency() {
        return latency;
    }

    public void setLatency(Long latency) {
        this.latency = latency;
    }
}
