package com.example.unicourse.models.common;
public class CommonResponse<T> {

    private String message;
    private Number status;
    private T data;

    public CommonResponse() {
    }

    public CommonResponse(String message, Number status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Number getStatus() {
        return status;
    }

    public void setStatus(Number status) {
        this.status = status;
    }

    public T getData() {
        return  data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CoursesResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
