package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointService {

    private CurvePointRepository curvePointRepository;

    public void delete(Integer id) {
        curvePointRepository.deleteById(id);
    }

    public List<CurvePoint> getAll() {
        return curvePointRepository.findAll();
    }
    public CurvePoint get(Integer id) {
        return curvePointRepository.findById(id).orElseThrow();
    }
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }
}
