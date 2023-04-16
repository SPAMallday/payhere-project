package com.spamallday.payhere.entity;

import com.spamallday.payhere.dto.CafeProductDto;
import com.spamallday.payhere.util.NameConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/*
// BaseProductEntity를 구분하여 정의한 경우
// BaseProductEntity에 정의된 컬럼을 구체적으로 재정의
@AttributeOverrides({
        @AttributeOverride(name = "price", column = @Column(columnDefinition = "MEDIUMINT unsigned")),
        @AttributeOverride(name = "cost", column = @Column(columnDefinition = "MEDIUMINT unsigned"))
})*/

@Table(name = "product")
@Entity
@Getter
@SuperBuilder
// NoArgs 생성자를 Protected 레벨에서만 접근(JPA 프록시 생성에 필요)할 수 있게하여 객체 생성 시 안정성을 높임
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", columnDefinition = "BIGINT unsigned")
    private Long id;

    private String category;

    private Integer price;

    private Integer cost;

    private String name;

    @Column(length = 1000)
    private String info;

    @Column(length = 14)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;

    @Column(length = 5)
    private String size;

    @Column(name = "word_name")
    private String wordName;

    // 다대일 맵핑
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", columnDefinition = "INT(10) unsigned")
    private Owner owner;

    @Builder
    public CafeProduct(String category, Integer price, Integer cost, String name, String info, String code, Date expire, String size, String wordName, Owner owner) {
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.info = info;
        this.code = code;
        this.expire = expire;
        this.size = size;
        this.wordName = NameConverter.toConsonant(name);
        this.owner = owner;
    }

    // Property 변경
    public void changeProperties(CafeProductDto cafeProductDto) {
        this.category = cafeProductDto.getCategory();
        this.price = cafeProductDto.getPrice();
        this.cost = cafeProductDto.getCost();
        this.name = cafeProductDto.getName();
        this.info = cafeProductDto.getInfo();
        this.code = cafeProductDto.getCode();
        this.expire = Timestamp.valueOf(LocalDateTime.parse(cafeProductDto.getExpire(), DateTimeFormatter.ISO_DATE_TIME));
        this.size = cafeProductDto.getSize();
        this.wordName = NameConverter.toConsonant(cafeProductDto.getName());
    }

    // Property 1개 변경
    public void changeCategory(String category) {
        this.category = category;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeCost(Integer cost) {
        this.cost = cost;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeInfo(String info) {
        this.info = info;
    }

    public void changeCode(String code) {
        this.code = code;
    }

    public void changeExpire(String expire) {
        this.expire = Timestamp.valueOf(LocalDateTime.parse(expire, DateTimeFormatter.ISO_DATE_TIME));
    }

    public void changeSize(String size) {
        this.size = size;
    }

    public void changeWordName(String wordName) {
        this.wordName = NameConverter.toConsonant(getName());
    }
}
