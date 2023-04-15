package com.spamallday.payhere.repository;

import com.spamallday.payhere.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository<T extends Member> extends JpaRepository<T, Integer> {
    public Member findByNumber(String number);
}
