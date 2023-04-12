package com.spamallday.payhere.entity;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "owner")  // 테이블 이름에 맞게 생성
@Entity // Entity로 명시
@Getter
// NoArgs 생성자를 Protected 레벨에서만 접근(JPA 프록시 생성에 필요)할 수 있게하여 객체 생성 시 안정성을 높임
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {

    @Id
    @Column(name = "user_id", columnDefinition = "INT(10) unsigned")   // Entity와 db의 컬럼 이름을 맵핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에서 맡음 MySQL이기 때문에 Auto Increment
    private Integer id;

    @NotNull
    @Column(length = 11)    // db에서 가변길이를 지정
    private String number;

    @NotNull
    @Column(columnDefinition="char(64)")    // VARCHAR가 아닌 CHAR로 고정
    private String password;

    @NotNull
    @Column(columnDefinition="char(64)")    // VARCHAR가 아닌 CHAR로 고정
    private String salt;

    //양방향 매핑을 위해 추가
    @OneToMany(mappedBy = "owner")
    private List<Product> products = new ArrayList<>();

    @Builder
    private Owner(Integer id, String number, String password, String salt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.salt = salt;
    }
}
