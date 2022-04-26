package com.example.service.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DBManager {
    public static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    public static EntityManager entityManager =
            entityManagerFactory.createEntityManager();

    public static EntityTransaction entityTransaction =
            entityManager.getTransaction();
}
