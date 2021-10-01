package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.dto.BaseDTO;
import com.example.ski_resort.baranukov.entity.BaseEntity;

import java.util.Collection;

public interface BaseCRUDService<T extends BaseEntity, Y extends BaseDTO> {
    Collection<Y> getAll();
    Y get(Long id);
    T save(T t);
    T update(T t);
    void delete(Long id);
}
