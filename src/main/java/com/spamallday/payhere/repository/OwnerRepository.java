package com.spamallday.payhere.repository;

import com.spamallday.payhere.entity.Member;
import com.spamallday.payhere.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

//public interface OwnerRepository extends JpaRepository<Owner, Integer> {
public interface OwnerRepository extends MemberRepository<Owner> {
}
