package com.github.yuizho.controller;

import com.github.yuizho.annotation.Transactional;
import com.github.yuizho.service.SampleService2;

public class SampleController2 {
    private final SampleService2 sampleService;

    public SampleController2() {
        this.sampleService = new SampleService2();
    }

    @Transactional
    public String save() {
        sampleService.save(2);
        return "OK";
    }

    public String save2() {
        sampleService.save2(2);
        return "OK";
    }
}
