package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {

    @Autowired
    private  BidListRepository bidListRepository;

    public void delete(Integer id) {
        bidListRepository.deleteById(id);
    }

    public List<BidList> getAll() {
        return bidListRepository.findAll();
    }
    public BidList get(Integer id) {
        return bidListRepository.findById(id).orElseThrow();
    }
    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }
}
