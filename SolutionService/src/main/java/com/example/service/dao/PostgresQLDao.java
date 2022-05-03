package com.example.service.dao;

import com.example.service.entity.ResultsEntity;

public interface PostgresQLDao {
    void save(ResultsEntity value);

    ResultsEntity find(Class<ResultsEntity> entityClass, int primaryKey);

    ResultsEntity delete(ResultsEntity value);

    ResultsEntity deleteById(Class<ResultsEntity> entityClass, int primaryKey);
}
