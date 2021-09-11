package com.example.appnotestest.domain;

public interface Callback<T> {
    void onSuccess(T data);
}