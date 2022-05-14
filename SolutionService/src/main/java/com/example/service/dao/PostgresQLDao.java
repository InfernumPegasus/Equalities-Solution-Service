package com.example.service.dao;

/**
 * Interface for DataBase interaction
 * @param <C> entity class
 * @param <K> primary key
 */
public interface PostgresQLDao<C, K> {
    /**
     * Method which is used to
     * save entity in DataBase.
     * @param value value to store
     */
    void save(C value);

    /**
     * Method which is used to find
     * value by provided key of
     * {@link C} class
     * @param entityClass parameter class
     * @param primaryKey key for search
     * @return found value
     */
    C find(Class<C> entityClass, K primaryKey);

    /**
     * Method which is used to delete record by value
     * @param value value to delete
     * @return deleted value
     */
    C delete(C value);

    /**
     * Method which is used to delete value
     * of class {@link C} by {@link K} key
     * @param entityClass class of value
     * @param primaryKey key to delete
     * @return deleted value
     */
    C deleteByKey(Class<C> entityClass, K primaryKey);
}
