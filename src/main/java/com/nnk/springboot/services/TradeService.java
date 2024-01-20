package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    public void delete(Integer id) {
        tradeRepository.deleteById(id);
    }

    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }
    public Trade get(Integer id) {
        return tradeRepository.findById(id).orElseThrow();
    }
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }
}
