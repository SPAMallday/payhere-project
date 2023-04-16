package com.spamallday.payhere.repository;

import com.spamallday.payhere.entity.CafeProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CafeProductRepository extends JpaRepository<CafeProduct, Long> {
    public CafeProduct findByIdAndOwnerId(Long id, Integer ownerId);
    public void deleteByIdAndOwnerId(Long id, Integer ownerId);

    // 최신부터 내림차순으로 구현하는 cursor based pagination
    public List<CafeProduct> findAllByOwnerIdOrderByIdDesc(Integer ownerId, Pageable page);
    @Query(value =
            "    select" +
            "        p.item_id as item_id, " +
                    "p.category as category, " +
                    "p.price as price, " +
                    "p.cost as cost, " +
                    "p.name as name, " +
                    "p.info as info, " +
                    "p.code as code, " +
                    "p.expire as expire, " +
                    "p.size as size, " +
                    "p.owner_id as owner_id " +
            "    from " +
            "        product p " +
            "    left outer join " +
            "        member o " +
            "            on p.owner_id=o.user_id " +
            "    where" +
            "        o.user_id=:ownerId " +
            "        and " +
            "        p.item_id < :id " +
            "    order by " +
                    "p.item_id desc limit :page ", nativeQuery = true)
    public List<CafeProduct> findByOwnerIdAndIdLessThanOrderByIdDesc(
            @Param("id") Long id, @Param("ownerId") Integer ownerId, @Param("page") Integer pageSize);

    @Query (value =
            "    select" +
            "        p.item_id" +
            "    from" +
            "        product p" +
            "    left outer join" +
            "        member o" +
            "            on p.owner_id=o.user_id " +
            "    where" +
            "        o.user_id=:ownerId " +
            "        and" +
            "        p.item_id < :id limit 1", nativeQuery = true)
    public Optional<BigInteger> existsByIdLessThan(@Param("id") Long id, @Param("ownerId") Integer ownerId);

    @Query (value =
            "    select" +
            "        p.item_id as item_id, " +
                    "p.category as category, " +
                    "p.price as price, " +
                    "p.cost as cost, " +
                    "p.name as name, " +
                    "p.info as info, " +
                    "p.code as code, " +
                    "p.expire as expire, " +
                    "p.size as size, " +
                    "p.word_name as word_name, " +
                    "p.owner_id as owner_id " +
            "    from " +
            "        product p " +
            "    left outer join " +
            "        member o " +
            "            on p.owner_id=o.user_id " +
            "    where" +
            "        o.user_id=:ownerId " +
            "        and " +
            "        p.name like %:keyword%", nativeQuery = true)
    public List<CafeProduct> findByOwnerIdNameContaining(@Param("ownerId") Integer ownerId, @Param("keyword") String keyword);
    @Query (value =
            "    select" +
                    "        p.item_id as item_id, " +
                    "p.category as category, " +
                    "p.price as price, " +
                    "p.cost as cost, " +
                    "p.name as name, " +
                    "p.info as info, " +
                    "p.code as code, " +
                    "p.expire as expire, " +
                    "p.size as size, " +
                    "p.word_name as word_name, " +
                    "p.owner_id as owner_id " +
                    "    from " +
                    "        product p " +
                    "    left outer join " +
                    "        member o " +
                    "            on p.owner_id=o.user_id " +
                    "    where" +
                    "        o.user_id=:ownerId " +
                    "        and " +
                    "        p.word_name like %:keyword%", nativeQuery = true)
    public List<CafeProduct> findByWordNameContaining(@Param("ownerId") Integer ownerId, @Param("keyword") String keyword);
}
