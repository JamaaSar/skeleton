package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    private Integer curveId;
    Timestamp asOfDate;
    Double term;
    private Double value;
    private Timestamp creationDate;

}
