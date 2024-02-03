package com.nnk.springboot.service;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;
    @InjectMocks
    private BidListService bidListService;

    private BidList bidList = new BidList();
    private BidList bidList1 = new BidList();
    private List<BidList> tradeList = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        bidList.setBidListId(1);
        bidList.setAccount("Account 0");
        bidList.setType("Type 0");
        bidList.setBidQuantity(1d);


        bidList1.setBidListId(2);
        bidList1.setAccount("Account 1");
        bidList1.setType("Type 1");
        bidList1.setBidQuantity(2d);
        tradeList.add(bidList);
        tradeList.add(bidList1);

    }

    @Test
    @DisplayName("get All Trade")
    public void testGetAllTrade() {

        // Given.

        // When.
        when(bidListRepository.findAll()).thenReturn(tradeList);
        List<BidList> lists = bidListService.getAll();

        // Then.
        assertEquals(tradeList.size(), lists.size());
        verify(bidListRepository, times(1)).findAll();


    }
    @Test
    @DisplayName("get Trade by id")
    public void testGetTradeById() {

        // Given.

        // When.
        when(bidListRepository.findById(1)).thenReturn(Optional.ofNullable(bidList1));
        BidList res = bidListService.get(1);

        // Then.
        assertEquals(res.getBidListId(), bidList1.getBidListId());
        assertEquals(res.getAccount(), bidList1.getAccount());
        assertEquals(res.getBidQuantity(), bidList1.getBidQuantity());
        verify(bidListRepository, times(1)).findById(1);


    }

    @Test
    @DisplayName("save Trade")
    public void testSaveTrade() {

        // Given.
        BidList bidList2 = new BidList();
        bidList2.setBidListId(3);
        bidList2.setAccount("Account 2");
        bidList2.setType("Type 2");
        bidList2.setBidQuantity(3d);

        // When.
        bidListService.save(bidList2);

        // Then.
        verify(bidListRepository, times(1)).save(bidList2);


    }
    @Test
    @DisplayName("delete Trade")
    public void testDeleteTrade() {


        // When.
        bidListService.delete(3);

        // Then.
        verify(bidListRepository, times(1)).deleteById(3);


    }


}
