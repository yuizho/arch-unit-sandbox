package com.github.yuizho.service;

import com.github.yuizho.entity.Sample;
import com.github.yuizho.repository.SampleRepository;

public class SampleService {
    private final SampleRepository sampleRepository;

    public SampleService() {
        this.sampleRepository = new SampleRepository();
    }

    public void save() {
        sampleRepository.save(new Sample(1));
    }
}
