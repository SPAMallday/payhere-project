package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.CafeProductDto;
import com.spamallday.payhere.entity.CafeProduct;
import com.spamallday.payhere.exception.CustomErrorCode;
import com.spamallday.payhere.exception.CustomException;
import com.spamallday.payhere.repository.CafeProductRepository;
import com.spamallday.payhere.repository.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CafeProductServiceImpl implements CafeProductService {
    private final CafeProductRepository cafeProductRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CafeProductServiceImpl(CafeProductRepository cafeProductRepository, OwnerRepository ownerRepository) {
        this.cafeProductRepository = cafeProductRepository;
        this.ownerRepository = ownerRepository;
    }

    // 어노테이션으로 하지 못한 검증을 수행
    @Override
    public void validProperty(CafeProductDto cafeProductDto) {
        // 유통기한
            // YYYY-MM-DD'T'HH:mm:ss 형식 체크
        if (!Pattern.matches("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])T(0[0-9]|1[0-9]|2[0-3]):(0[1-9]|[0-5][0-9]):(0[1-9]|[0-5][0-9])$",
                cafeProductDto.getExpire())) {
            throw new CustomException(CustomErrorCode.EXPIRE_TYPE_ERROR);
        }
            // 이미 지났는지 확인
        else if (LocalDateTime.parse(cafeProductDto.getExpire()).isBefore(LocalDateTime.now())) {
            throw new CustomException(CustomErrorCode.EXPIRE_PASS_ERROR);
        }
        // 바코드
        if (!Pattern.matches("^[0-9]*$", cafeProductDto.getCode())) {
            throw new CustomException(CustomErrorCode.BARCODE_ERROR);
        }
        // 사이즈
        if (!cafeProductDto.getSize().equals("small") && !cafeProductDto.getSize().equals("large")) {
            throw new CustomException(CustomErrorCode.SIZE_WRONG_ERROR);
        }
    }

    @Transactional
    public void updateProperty(CafeProductDto cafeProductDto) throws Exception {
        CafeProduct res = cafeProductRepository.findByIdAndOwnerId(cafeProductDto.getId(), 1);
//        Optional<CafeProduct> res = cafeProductRepository.findById(cafeProductDto.getId());

        // 쿼리 결과가 존재하면
        if (res != null) {
            res.changeProperties(cafeProductDto);
        }
        // 쿼리 결과가 없으면
//        else {
//            cafeProductRepository.save(cafeProductDto.toEntity());
//        }
    }
}
