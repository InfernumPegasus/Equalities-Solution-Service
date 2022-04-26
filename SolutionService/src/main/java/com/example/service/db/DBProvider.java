package com.example.service.db;

import com.example.service.entity.ResultsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DBProvider {

    public static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    public static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static EntityTransaction entityTransaction = entityManager.getTransaction();

    public static void save(ResultsEntity resultsEntity) {
        entityTransaction.begin();

        // Check if exists
        entityManager.persist(resultsEntity);

        // Add to DB
        entityTransaction.commit();
    }

}

