package com.ehsandev.cs2340.model;

public class Response<T> {
    private int success;
    private String message;
    private T data;

    public Response(int success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
