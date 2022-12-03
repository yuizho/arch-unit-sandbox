package com.github.yuizho.repository;

import com.github.yuizho.entity.Sample;

public class SampleRepository {
    public void save(Sample sample) {
        System.out.println("save " + sample.id() + " data!!");
    }
}
