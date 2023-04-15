package com.spamallday.payhere.service;

import com.spamallday.payhere.dto.CafeProductDto;

public interface CafeProductService {
    void validProperty(CafeProductDto cafeProductDto);
    void updateProperty(Long id, CafeProductDto cafeProductDto) throws Exception;
    void removeItem(Long id) throws Exception ;
}
