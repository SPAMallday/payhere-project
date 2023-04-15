package com.spamallday.payhere.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Table(name = "member")  // 테이블 이름에 맞게 생성
@Entity // Entity로 명시
@Getter
@SuperBuilder
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
// NoArgs 생성자를 Protected 레벨에서만 접근(JPA 프록시 생성에 필요)할 수 있게하여 객체 생성 시 안정성을 높임
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에서 맡음 MySQL이기 때문에 Auto Increment
    @Column(name = "user_id", columnDefinition = "INT(10) unsigned")   // Entity와 db의 컬럼 이름을 맵핑
    private Integer id;

/*
    UUID로 PK를 사용하는 경우
    @Id
    @GeneratedValue(generator = "uuid2") // 기본 키를 uuid로 생성
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID id;
*/

    @NotNull
    @Column(length = 11)    // db에서 가변길이를 지정
    private String number;

    @NotNull
    @Column(columnDefinition="char(64)")    // VARCHAR가 아닌 CHAR로 고정
    private String password;

    @NotNull
    @Column(columnDefinition="char(64)")    // VARCHAR가 아닌 CHAR로 고정
    private String salt;
}
