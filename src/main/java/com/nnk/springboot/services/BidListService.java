package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {

    private  BidListRepository bidListRepository;

    public void delete(Integer id) {
        bidListRepository.deleteById(id);
    }
    public void update(Integer id, BidList bidList) {
        BidList update = bidListRepository.findById(id).orElseThrow();

        bidListRepository.save(bidList);

    }
    public List<BidList> getAll() {
        return bidListRepository.findAll();
    }
    public BidList get(Integer id) {
        return bidListRepository.findById(id).orElseThrow();
    }
    public BidList add(BidList bid) {

        return bidListRepository.save(bid);
    }
}
