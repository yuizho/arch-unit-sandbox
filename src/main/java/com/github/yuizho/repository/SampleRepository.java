package com.github.yuizho.repository;

import com.github.yuizho.entity.Sample;

public class SampleRepository {
    public Sample find(int id) {
        return new Sample(id);
    }

    public void save(Sample sample) {
        System.out.println("save " + sample.id() + " data!!");
    }
}
