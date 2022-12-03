package com.github.yuizho.controller;

import com.github.yuizho.annotation.Transactional;
import com.github.yuizho.service.SampleService;

public class SampleController2 {
    private final SampleService sampleService;

    public SampleController2() {
        this.sampleService = new SampleService();
    }

    @Transactional
    public String save() {
        sampleService.save(2);
        return "OK";
    }
}
