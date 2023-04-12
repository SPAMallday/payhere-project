package com.spamallday.payhere.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "product")
@Entity
@Getter
// NoArgs 생성자를 Protected 레벨에서만 접근(JPA 프록시 생성에 필요)할 수 있게하여 객체 생성 시 안정성을 높임
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", columnDefinition = "BIGINT unsigned")
    private Long id;

    @Column(length = 255)
    private String category;

    @Column(columnDefinition = "MEDIUMINT unsigned")
    private String price;

    @Column(columnDefinition = "MEDIUMINT unsigned")
    private String cost;

    @Column(length = 255)
    private String name;

    @Column(length = 1000)
    private String info;

    @Column(length = 14)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;

    @Column(length = 5)
    private String size;

    // 다대일 맵핑
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", columnDefinition = "INT(10) unsigned")
    private Owner owner;
}
