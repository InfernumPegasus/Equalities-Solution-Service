package com.example.service.controllers;

import com.example.service.dao.PostgresQLDaoImpl;
import com.example.service.entity.ResultsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for
 * manipulations with DB.
 */
@RestController
@RequestMapping("/db")
public class DataBaseController {

    private final PostgresQLDaoImpl resultDAO;

    @Autowired
    public DataBaseController(PostgresQLDaoImpl resultDAO) {
        this.resultDAO = resultDAO;
    }

    /**
     * Deletes record from DB by id
     * @param id id of record
     * @return {@link ResponseEntity<String>} with status code OK if success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(
            @PathVariable(value = "id") int id
    ) {
        return new ResponseEntity<>(
                resultDAO.deleteByKey(ResultsEntity.class, id) + " deleted",
                HttpStatus.OK);
    }

    /**
     * Gets record from DB by id
     * @param id id of record
     * @return {@link ResponseEntity<String>} with status code OK if success
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultsEntity> getById(
            @PathVariable(value = "id") int id
    ) {
        return new ResponseEntity<>(
                resultDAO.find(ResultsEntity.class, id),
                HttpStatus.OK);
    }
}
