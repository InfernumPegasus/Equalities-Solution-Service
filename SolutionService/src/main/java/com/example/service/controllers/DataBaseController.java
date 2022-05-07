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

@RestController
@RequestMapping("/db")
public class DataBaseController {

    private PostgresQLDaoImpl resultDAO;

    @Autowired
    public void setResultDAO(PostgresQLDaoImpl resultDAO) {
        this.resultDAO = resultDAO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(
            @PathVariable(value = "id") int id
    ) {
        return new ResponseEntity<>(
                resultDAO.deleteById(ResultsEntity.class, id) + " deleted"
                ,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultsEntity> getRecord(
            @PathVariable(value = "id") int id
    ) {
        return new ResponseEntity<>(
                resultDAO.find(ResultsEntity.class, id),
                HttpStatus.OK);
    }
}
