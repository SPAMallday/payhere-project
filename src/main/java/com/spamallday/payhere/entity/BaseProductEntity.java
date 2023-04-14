package com.spamallday.payhere.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@MappedSuperclass
public abstract class BaseProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", columnDefinition = "BIGINT unsigned")
    private Long id;

    @Column(length = 255)
    private String category;

    private Integer price;

    private Integer cost;

    @Column(length = 255)
    private String name;

    @Column(length = 1000)
    private String info;
}
