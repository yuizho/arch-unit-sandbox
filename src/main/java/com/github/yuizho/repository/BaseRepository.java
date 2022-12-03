package com.github.yuizho.repository;

public interface BaseRepository<T> {
    T find(int id);

    void save(T entity);
}
