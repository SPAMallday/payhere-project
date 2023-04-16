package com.spamallday.payhere.repository;

import com.spamallday.payhere.entity.Owner;

//public interface OwnerRepository extends JpaRepository<Owner, Integer> {
public interface OwnerRepository extends MemberRepository<Owner> {
}
