package com.plume.code.controller.vo;

import lombok.Data;

/**
 * @author : pdl
 * @date : 2021/6/29 13:43
 */
@Data
public class R<T> {
    private T data;
    private boolean success = true;
    private String message;

    public static R<Object> OK = new R<>();

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.data = data;
        return r;
    }

    public static R<Object> fail(String message) {
        R<Object> r = new R<>();
        r.message = message;
        r.success = false;
        return r;
    }
}
