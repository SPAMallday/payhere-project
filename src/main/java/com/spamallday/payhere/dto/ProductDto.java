package com.spamallday.payhere.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ProductDto {
    private Long id;
    private String category;
    private Integer price;
    private Integer cost;
    private String name;
    private String info;
    private String code;
    private Date expire;
    private String size;
}
