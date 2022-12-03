package com.github.yuizho.controller;

import com.github.yuizho.service.SampleService;

import java.beans.Transient;

public class SampleController2 {
    private final SampleService sampleService;

    public SampleController2() {
        this.sampleService = new SampleService();
    }

    @Transient
    public String save() {
        sampleService.save(2);
        return "OK";
    }
}
