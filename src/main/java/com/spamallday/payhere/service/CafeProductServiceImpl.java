package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.CafeProductDto;
import com.spamallday.payhere.entity.CafeProduct;
import com.spamallday.payhere.exception.CustomErrorCode;
import com.spamallday.payhere.exception.CustomException;
import com.spamallday.payhere.repository.CafeProductRepository;
import com.spamallday.payhere.repository.OwnerRepository;
import com.spamallday.payhere.util.CursorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CafeProductServiceImpl implements CafeProductService {

    private final int ownerId = 1;

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

    @Override
    @Transactional
    public void updateProperty(Long id, CafeProductDto cafeProductDto) throws Exception {
        // TODO 사장님 ID 넣어서 수행하게 바꿔야함
        CafeProduct res = cafeProductRepository.findByIdAndOwnerId(id, ownerId);

        // 쿼리 결과가 존재하면
        if (res != null) {
            res.changeProperties(cafeProductDto);
        }
        // 쿼리 결과가 없으면
//        else {
//            cafeProductRepository.save(cafeProductDto.toEntity());
//        }
    }

    @Override
    @Transactional
    public void registerItem(CafeProductDto cafeProductDto) throws Exception {
        // TODO 사용자 정보 받아와서 toEntity에 전달하여 Owner 등록
        cafeProductRepository.save(cafeProductDto.toEntity());
    }

    @Override
    @Transactional
    public void removeItem(Long id) throws Exception  {
        // TODO 사장님 ID 넣어서 수행하게 바꿔야함
        cafeProductRepository.deleteByIdAndOwnerId(id, ownerId);
    }

    /* 커서 위치를 기준으로 Page 만큼 내림차순 조회 */
    @Override
    public CursorResult<CafeProduct> getItemList(Long cursorId, Pageable page) {
        // 페이징 조회
        final List<CafeProduct> items = getItems(cursorId, page);
        // 더 진행할 수 있는 데이터가 있는지
        final Long lastIdOfList = items.isEmpty() ?
                null : items.get(items.size() - 1).getId();

        return new CursorResult<>(items, hasNext(lastIdOfList));
    }

    @Override
    public CafeProduct getItem(Long id) {
        // TODO 사장님 ID 넣어서 수행하게 바꿔야함
        return cafeProductRepository.findByIdAndOwnerId(id, ownerId);
    }

    // 아래는 페이징을 위해 Service 내에서만 사용하는 메서드들

    /* 커서 ID가 없다면 가장 최신부터 내림차순으로 page 만큼 가져오고
    *  커서가 있으면 해당 커서의 위치부터 내림차순으로 page 만큼 가져옴
    * */
    private List<CafeProduct> getItems(Long id, Pageable page) {
        return (id == null || id == 0L) ?
                cafeProductRepository.findAllByOwnerIdOrderByIdDesc(ownerId, page) :
                cafeProductRepository.findByOwnerIdAndIdLessThanOrderByIdDesc(id, ownerId, page.getPageSize());
    }

    /* 조회한 내용의 마지막 데이터에서 다음에도 데이터가 있는 지 확인하여
    *  존재하면 true, 없다면 false
    * */
    private Boolean hasNext(Long id) {
        if (id == null) return false;
        return cafeProductRepository.existsByIdLessThan(id, ownerId).isPresent();
    }
}
