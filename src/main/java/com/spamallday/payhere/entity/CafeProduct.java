package com.spamallday.payhere.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "product")
@Entity
@Getter
// ProductBaseEntity에 정의된 컬럼을 구체적으로 재정의
@AttributeOverrides({
        @AttributeOverride(name = "price", column = @Column(columnDefinition = "MEDIUMINT unsigned")),
        @AttributeOverride(name = "cost", column = @Column(columnDefinition = "MEDIUMINT unsigned"))
})
// NoArgs 생성자를 Protected 레벨에서만 접근(JPA 프록시 생성에 필요)할 수 있게하여 객체 생성 시 안정성을 높임
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeProduct extends BaseProductEntity {

    // 아래는 카페에서 적용할 수 있는 컬럼들
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
