package com.prueba.tec.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter
public class GetPricesResponse {
    private String productId;
    private String brandId;
    private String priceList;
    private Timestamp startDate;
    private Timestamp endDate;
    private Double price;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Class GetPricesResponse {\n");
        sb.append("    productId: ").append(toIdentedString(productId)).append("\n");
        sb.append("    brandId: ").append(toIdentedString(brandId)).append("\n");
        sb.append("    priceList: ").append(toIdentedString(priceList)).append("\n");
        sb.append("    startDate: ").append(toIdentedString(formatDate(startDate))).append("\n");
        sb.append("    endDate: ").append(toIdentedString(formatDate(endDate))).append("\n");
        sb.append("    price: ").append(toIdentedString(price)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) {
            return "null";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    private String toIdentedString(java.lang.Object o){
        if(o==null){
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
