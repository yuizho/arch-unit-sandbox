package com.github.yuizho.controller;

import com.github.yuizho.service.SampleService;

public class SampleController {
    private final SampleService sampleService;

    public SampleController() {
        this.sampleService = new SampleService();
    }

    public String save() {
        sampleService.save();
        return "OK";
    }
}
