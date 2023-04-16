package com.spamallday.payhere.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "member")  // 테이블 이름에 맞게 생성
@Entity // Entity로 명시
@Getter
@SuperBuilder
// NoArgs 생성자를 Protected 레벨에서만 접근(JPA 프록시 생성에 필요)할 수 있게하여 객체 생성 시 안정성을 높임
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("OWNER")    // DTYPE을 특정
public class Owner extends Member {

//    stackoverflow 문제 처리 가능하면 적용!
//    //양방향 매핑을 위해 추가
//    @OneToMany(mappedBy = "owner")
//    private List<CafeProduct> cafeProducts = new ArrayList<>();

}
