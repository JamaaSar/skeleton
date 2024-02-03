package com.nnk.springboot.service;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;
    @InjectMocks
    private CurvePointService curvePointService;

    private CurvePoint curvePoint = new CurvePoint();
    private CurvePoint curvePoint1 = new CurvePoint();
    private List<CurvePoint> curvePointList = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        curvePoint.setCurveId(1);
        curvePoint.setTerm(1d);
        curvePoint.setValue(10d);


        curvePoint1.setCurveId(2);
        curvePoint1.setTerm(2d);
        curvePoint1.setValue(20d);
        curvePointList.add(curvePoint);
        curvePointList.add(curvePoint1);

    }

    @Test
    @DisplayName("get All Trade")
    public void testGetAllTrade() {

        // Given.

        // When.
        when(curvePointRepository.findAll()).thenReturn(curvePointList);
        List<CurvePoint> lists = curvePointService.getAll();

        // Then.
        assertEquals(curvePointList.size(), lists.size());
        verify(curvePointRepository, times(1)).findAll();


    }
    @Test
    @DisplayName("get Trade by id")
    public void testGetTradeById() {

        // Given.

        // When.
        when(curvePointRepository.findById(1)).thenReturn(Optional.ofNullable(curvePoint1));
        CurvePoint res = curvePointService.get(1);

        // Then.
        assertEquals(res.getCurveId(), curvePoint1.getCurveId());
        assertEquals(res.getTerm(), curvePoint1.getTerm());
        assertEquals(res.getValue(), curvePoint1.getValue());
        verify(curvePointRepository, times(1)).findById(1);


    }

    @Test
    @DisplayName("save Trade")
    public void testSaveTrade() {

        // Given.
        CurvePoint bidList2 = new CurvePoint();
        bidList2.setCurveId(3);
        bidList2.setTerm(3d);
        bidList2.setValue(30d);

        // When.
        curvePointService.save(bidList2);

        // Then.
        verify(curvePointRepository, times(1)).save(bidList2);


    }
    @Test
    @DisplayName("delete Trade")
    public void testDeleteTrade() {


        // When.
        curvePointService.delete(3);

        // Then.
        verify(curvePointRepository, times(1)).deleteById(3);


    }


}
