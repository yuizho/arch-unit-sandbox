package com.github.yuizho.controller;

import com.github.yuizho.service.SampleService;

public class SampleController {
    private final SampleService sampleService;

    public SampleController() {
        this.sampleService = new SampleService();
    }

    public String save_NG_NoAnnotation() {
        sampleService.save(1);
        return "OK";
    }

    public String find(int i) {
        return sampleService.find(i).toString();
    }
}
