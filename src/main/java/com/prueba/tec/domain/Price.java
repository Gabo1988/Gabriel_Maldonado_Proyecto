package com.prueba.tec.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "PRICES")
@Data
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRAND_ID")
    private String brandId;

    @Column(name = "START_DATE")
    private Timestamp startDate;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "PRICE_LIST")
    private int priceList;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "PRIORITY")
    private int priority;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "CURR")
    private String curr;

    @Column(name = "LAST_UPDATE")
    private Timestamp lastUpdate;

    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    // Constructor sin argumentos
    public Price() {
    }
    public Price(String brandId, Timestamp startDate, Timestamp endDate, int priceList, String productId,
                 int priority, double price, String curr, Timestamp lastUpdate, String lastUpdateBy) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.curr = curr;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
}
