package com.nnk.springboot.service;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.CurrentUserDTO;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;
    @InjectMocks
    private TradeService tradeService;

    private Trade trade = new Trade();
    private Trade trade1 = new Trade();
    private List<Trade> tradeList = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        trade.setTradeId(1);
        trade.setAccount("Trade Account 0");
        trade.setType("Type 0");


        trade1.setTradeId(2);
        trade1.setAccount("Trade Account 1");
        trade1.setType("Type 1");
        tradeList.add(trade);
        tradeList.add(trade1);

    }

    @Test
    @DisplayName("get All Trade")
    public void testGetAllTrade() {

        // Given.

        // When.
        when(tradeRepository.findAll()).thenReturn(tradeList);
        List<Trade> tradeList1 = tradeService.getAll();

        // Then.
        assertEquals(tradeList.size(), tradeList1.size());
        verify(tradeRepository, times(1)).findAll();


    }
    @Test
    @DisplayName("get Trade by id")
    public void testGetTradeById() {

        // Given.

        // When.
        when(tradeRepository.findById(1)).thenReturn(Optional.ofNullable(trade1));
        Trade res = tradeService.get(1);

        // Then.
        assertEquals(res.getTradeId(), trade1.getTradeId());
        assertEquals(res.getAccount(), trade1.getAccount());
        verify(tradeRepository, times(1)).findById(1);


    }

    @Test
    @DisplayName("save Trade")
    public void testSaveTrade() {

        // Given.
        Trade trade2 = new Trade();
        trade2.setTradeId(3);
        trade2.setAccount("Trade Account 2");
        trade2.setType("Type 2");

        // When.
        tradeService.save(trade2);

        // Then.
        verify(tradeRepository, times(1)).save(trade2);


    }
    @Test
    @DisplayName("delete Trade")
    public void testDeleteTrade() {

        // Given.

        // When.
        tradeService.delete(3);

        // Then.
        verify(tradeRepository, times(1)).deleteById(3);


    }


}
