package com.spamallday.payhere.dto;

import com.spamallday.payhere.entity.CafeProduct;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CafeProductDto {

    private Long id;

    @Size(max = 255, message = "카테고리 이름이 너무 깁니다.")
    @NotEmpty(message = "카테고리를 올바르게 입력해주십시오.")
    private String category;

    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    @NotNull(message = "가격을 올바르게 입력해주십시오.")
    private Integer price;

    @PositiveOrZero(message = "원가는 0 이상이어야 합니다.")
    @NotNull(message = "원가를 올바르게 입력해주십시오.")
    private Integer cost;

    @Size(max = 255, message = "이름이 너무 깁니다.")
    @NotEmpty(message = "이름 올바르게 입력해주십시오.")
    private String name;

    @Size(max = 1000, message = "설명이 너무 깁니다.")
    @NotEmpty(message = "정보를 올바르게 입력해주십시오.")
    private String info;

    @Size(max = 14, message = "바코드가 너무 깁니다.")
    @NotEmpty(message = "바코드를 올바르게 입력해주십시오.")
    private String code;

    @Size(max = 19, message = "날짜가 너무 깁니다.")
    @NotEmpty(message = "유통기한을 올바르게 입력해주십시오.")
    private String expire;

    @NotEmpty(message = "사이즈를 올바르게 입력해주십시오.")
    private String size;

    public CafeProduct toEntity() {

        return  CafeProduct.builder()
                    .category(getCategory())
                    .price(getPrice())
                    .cost(getCost())
                    .name(getName())
                    .info(getInfo())
                    .code(getCode())
                    // String -> LocalDateTime -> Date로 변경
                    .expire(Timestamp.valueOf(LocalDateTime.parse(getExpire(), DateTimeFormatter.ISO_DATE_TIME)))
                    .size(getSize())
                    .build();
    }

    public static CafeProductDto fromEntity(CafeProduct cafeProduct) {
        CafeProductDto convert = new CafeProductDto();

        convert.setId(cafeProduct.getId());
        convert.setCategory(cafeProduct.getCategory());
        convert.setPrice(cafeProduct.getPrice());
        convert.setCost(cafeProduct.getCost());
        convert.setName(cafeProduct.getName());
        convert.setInfo(cafeProduct.getInfo());
        convert.setCode(cafeProduct.getCode());
        convert.setExpire(cafeProduct.getExpire().toString());
        convert.setSize(cafeProduct.getSize());

        return convert;
    }
}
