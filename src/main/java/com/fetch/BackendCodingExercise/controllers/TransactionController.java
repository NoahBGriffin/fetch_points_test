package com.fetch.BackendCodingExercise.controllers;

import com.fetch.BackendCodingExercise.dao.TransactionRecordDAO;
import com.fetch.BackendCodingExercise.dao.TransactionRecordMemoryDAO;
import com.fetch.BackendCodingExercise.exception.PointsOverdraftException;
import com.fetch.BackendCodingExercise.model.TransactionRecord;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TransactionController {

    private TransactionRecordDAO transactionRecordDAO;

    public TransactionController() {
        this.transactionRecordDAO = new TransactionRecordMemoryDAO();
    }

    @RequestMapping(path = "/add-transaction", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public TransactionRecord add(@RequestBody TransactionRecord transaction) throws PointsOverdraftException {
        return transactionRecordDAO.addTransaction(transaction);
    }

    @RequestMapping(path = "/point-balance", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Integer> getPointBalances() {
        return transactionRecordDAO.pointBalances();
    }

}
