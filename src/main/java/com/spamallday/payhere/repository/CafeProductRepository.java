package com.spamallday.payhere.repository;

import com.spamallday.payhere.entity.CafeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeProductRepository extends JpaRepository<CafeProduct, Long> {
    public CafeProduct findByIdAndOwnerId(Long id, Integer ownerId);
    public void deleteByIdAndOwnerId(Long id, Integer ownerId);
}
