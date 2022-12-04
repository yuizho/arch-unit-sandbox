package com.github.yuizho.service;

import com.github.yuizho.annotation.Transactional;
import com.github.yuizho.entity.Sample;
import com.github.yuizho.repository.SampleRepository;
import com.github.yuizho.repository.SampleRepository2;

public class SampleService2 {
    private final SampleRepository sampleRepository;
    private final SampleRepository2 sampleRepository2;

    public SampleService2() {
        this.sampleRepository = new SampleRepository();
        this.sampleRepository2 = new SampleRepository2();
    }

    public void save_NG_NotUserdMethod(int id) {
        sampleRepository.save(new Sample(id));
    }

    @Transactional
    public void save(int id) {
        sampleRepository2.save(new Sample(id));
    }

    public Sample find(int id) {
        return sampleRepository.find(id);
    }
}
