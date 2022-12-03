package com.github.yuizho.service;

import com.github.yuizho.entity.Sample;
import com.github.yuizho.repository.SampleRepository;

public class SampleService2 {
    private final SampleRepository sampleRepository;

    public SampleService2() {
        this.sampleRepository = new SampleRepository();
    }

    public void save(int id) {
        sampleRepository.save(new Sample(id));
    }

    public Sample find(int id) {
        return sampleRepository.find(id);
    }
}
