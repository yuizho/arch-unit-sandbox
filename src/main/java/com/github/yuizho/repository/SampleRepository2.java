package com.github.yuizho.repository;

import com.github.yuizho.entity.Sample;

public class SampleRepository2 implements BaseRepository<Sample> {
    @Override
    public Sample find(int id) {
        return new Sample(id);
    }
}
