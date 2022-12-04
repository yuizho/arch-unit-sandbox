package com.github.yuizho.controller;

import com.github.yuizho.annotation.Transactional;
import com.github.yuizho.service.SampleService;
import com.github.yuizho.service.SampleService2;

public class SampleController2 {
    private final SampleService sampleService;
    private final SampleService2 sampleService2;

    public SampleController2() {
        this.sampleService = new SampleService();
        this.sampleService2 = new SampleService2();
    }

    @Transactional
    public String save_OK_AnnotatedInController() {
        sampleService.save(2);
        return "OK";
    }

    @Transactional
    private String save_NG_Private() {
        sampleService.save(2);
        return "OK";
    }

    public String save_OK_AnnotatedInService() {
        sampleService2.save(2);
        return "OK";
    }
}
