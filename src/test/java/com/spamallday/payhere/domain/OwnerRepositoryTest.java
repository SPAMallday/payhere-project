package com.spamallday.payhere.domain;

import com.spamallday.payhere.entity.Owner;
import com.spamallday.payhere.repository.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class OwnerRepositoryTest {
    @Autowired
    OwnerRepository ownerRepository;

    @AfterEach    //@Test 어노테이션이 붙은 메소드가 각각 실행된 후 자동으로 실행됨
    public void cleanup() {
        ownerRepository.deleteAll();
    }

    @Test
    public void saveAndReadPosts() {
        String number = "01012345678";
        String password = "C8BD6FDFAECAA720A6B30527E5E97AE4891AAD1D713991ED5480F759DA195945";
        String salt = "63479AD69A090B258277EC8FBA6F99419A2FFB248981510657C944CCD1148E97";

        Owner saved = ownerRepository.save(Owner.builder().number(number).password(password).salt(salt).build());

        Owner res = ownerRepository.findById(saved.getId()).get();

        try {
            log.info("시작 : Owner 등록 및 조회 테스트");
            log.info("ID : " + res.getId());
            Assertions.assertThat(res.getNumber()).isEqualTo(number);
            Assertions.assertThat(res.getPassword()).isEqualTo(password);
            Assertions.assertThat(res.getSalt()).isEqualTo(salt);
        }
        finally {
            log.info("종료 : Owner 등록 및 조회 테스트");
        }

    }
}
