package com.github.yuizho.repository;

public interface BaseRepository<T> {
    T find(int id);

    default void save(T entity) {
        System.out.println("save " + entity + " data!!");
    }
}
