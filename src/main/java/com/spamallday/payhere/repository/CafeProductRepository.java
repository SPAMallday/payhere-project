package com.spamallday.payhere.repository;

import com.spamallday.payhere.entity.CafeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeProductRepository extends JpaRepository<CafeProduct, Integer> {
}
