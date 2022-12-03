package com.github.yuizho.repository;

import com.github.yuizho.entity.Sample;

public class SampleRepository implements BaseRepository<Sample> {
    @Override
    public Sample find(int id) {
        return new Sample(id);
    }

    @Override
    public void save(Sample sample) {
        System.out.println("save " + sample.id() + " data!!");
    }
}
