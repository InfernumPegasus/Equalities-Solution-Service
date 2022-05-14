package com.example.service.dao;

import com.example.service.entity.ResultsEntity;
import com.example.service.logger.MyLogger;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Implementation of {@link PostgresQLDao} interface.
 */
@Repository
@Qualifier("postgresData")
public class PostgresQLDaoImpl implements PostgresQLDao<ResultsEntity, Integer> {

    private final EntityManagerFactory entityManagerFactory
            = Persistence.createEntityManagerFactory("default");

    private final EntityManager entityManager =
            entityManagerFactory.createEntityManager();

    private final EntityTransaction entityTransaction =
            entityManager.getTransaction();

    /**
     * Saves {@link ResultsEntity} in DataBase.
     * @param value value to store
     */
    @Override
    public void save(ResultsEntity value) {
        closeAliveTransaction();

        entityTransaction.begin();
        entityManager.persist(value);
        entityTransaction.commit();

        MyLogger.log(Level.INFO, value + " saved");
    }

    /**
     * Finds {@link ResultsEntity} by {@link Integer} id
     * @param entityClass parameter class
     * @param primaryKey key for search
     * @return found {@link ResultsEntity} value
     * @throws RuntimeException if no such value
     */
    @Override
    public ResultsEntity find(Class<ResultsEntity> entityClass, Integer primaryKey) {
        closeAliveTransaction();

        var found = entityManager.find(entityClass, primaryKey);

        if (found == null) {
            throw new RuntimeException("No record with id: " + primaryKey);
        }
        return found;
    }

    /**
     * Deletes {@link ResultsEntity} value from DataBase
     * @param value value to delete
     * @return {@link ResultsEntity} deleted value
     */
    @Override
    public ResultsEntity delete(ResultsEntity value) {
        closeAliveTransaction();

        entityTransaction.begin();

        var found =
                entityManager.contains(value) ? value : entityManager.merge(value);

        entityManager.remove(found);
        entityTransaction.commit();

        MyLogger.log(Level.INFO, value + " deleted");

        return found;
    }

    /**
     * Deletes {@link ResultsEntity} value by {@link Integer} id from DataBase
     * @param entityClass class of value
     * @param primaryKey key to delete
     * @return {@link ResultsEntity} deleted value
     */
    @Override
    public ResultsEntity deleteByKey(Class<ResultsEntity> entityClass, Integer primaryKey) {
        return delete(find(entityClass, primaryKey));
    }

    /**
     * Closes alive transaction if needed
     */
    private void closeAliveTransaction() {
        if (entityTransaction.isActive()) {
            MyLogger.log(Level.ERROR, "Transaction is alive");
            entityTransaction.rollback();
        }
    }
}

