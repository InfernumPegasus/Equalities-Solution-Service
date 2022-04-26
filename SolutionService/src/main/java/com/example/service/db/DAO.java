package com.example.service.db;

import com.example.service.entity.ResultsEntity;

public interface DAO {
    void save(ResultsEntity value);

    ResultsEntity find(Class<ResultsEntity> entityClass, int primaryKey);

    ResultsEntity delete(ResultsEntity value);

    ResultsEntity deleteById(Class<ResultsEntity> entityClass, int primaryKey);
}
