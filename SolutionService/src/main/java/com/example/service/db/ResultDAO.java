package com.example.service.db;

import com.example.service.entity.ResultsEntity;
import com.example.service.logger.MyLogger;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;
import static com.example.service.db.DBManager.entityManager;
import static com.example.service.db.DBManager.entityTransaction;

@Service
public class ResultDAO implements DAO {

    @Override
    public void save(ResultsEntity value) {
        if (entityTransaction.isActive()) {
            MyLogger.log(Level.ERROR, "Transaction is alive");
            entityTransaction.rollback();
        }

        entityTransaction.begin();
        entityManager.persist(value);
        entityTransaction.commit();

        MyLogger.log(Level.INFO, value + " saved");
    }

    @Override
    public ResultsEntity find(Class<ResultsEntity> entityClass, int primaryKey) {
        if (entityTransaction.isActive()) {
            MyLogger.log(Level.ERROR, "Transaction is alive");
            entityTransaction.rollback();
        }

        var found = entityManager.find(entityClass, primaryKey);

        if (found == null) {
            throw new RuntimeException("No record with id: " + primaryKey);
        }
        return found;
    }

    @Override
    public ResultsEntity delete(ResultsEntity value) {
        if (entityTransaction.isActive()) {
            MyLogger.log(Level.ERROR, "Transaction is alive");
            entityTransaction.rollback();
        }

        entityTransaction.begin();

        var found =
                entityManager.contains(value) ? value : entityManager.merge(value);

        entityManager.remove(found);
        entityTransaction.commit();

        MyLogger.log(Level.INFO, value + " deleted");

        return found;
    }

    @Override
    public ResultsEntity deleteById(Class<ResultsEntity> entityClass, int primaryKey) {
        if (entityTransaction.isActive()) {
            MyLogger.log(Level.ERROR, "Transaction is alive");
            entityTransaction.rollback();
        }

        return delete(find(entityClass, primaryKey));
    }
}

