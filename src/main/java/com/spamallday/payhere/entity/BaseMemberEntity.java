package com.spamallday.payhere.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@MappedSuperclass
public abstract class BaseMemberEntity {
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

    @CreatedDate
    @Column(updatable = false, insertable = true)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime modified_at;
}
