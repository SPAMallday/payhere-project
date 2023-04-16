package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.CafeProductDto;
import com.spamallday.payhere.entity.CafeProduct;
import com.spamallday.payhere.util.CursorResult;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CafeProductService {
    void validProperty(CafeProductDto cafeProductDto);
    void updateProperty(Long id, CafeProductDto cafeProductDto) throws Exception;
    void registerItem(CafeProductDto cafeProductDto) throws Exception;
    void removeItem(Long id) throws Exception ;
    CursorResult<CafeProductDto> getItemList(Long cursorId, Pageable page);
    CafeProduct getItem(Long id);
    List<CafeProductDto> searchItem(String keyword) ;
}
