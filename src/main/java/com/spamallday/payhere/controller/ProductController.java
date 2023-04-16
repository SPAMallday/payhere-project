package com.spamallday.payhere.controller;

import com.spamallday.payhere.dto.CafeProductDto;
import com.spamallday.payhere.dto.JsonResponseDto;
import com.spamallday.payhere.entity.CafeProduct;
import com.spamallday.payhere.exception.CustomErrorCode;
import com.spamallday.payhere.exception.CustomException;
import com.spamallday.payhere.repository.CafeProductRepository;
import com.spamallday.payhere.service.CafeProductServiceImpl;
import com.spamallday.payhere.util.CursorResult;
import com.spamallday.payhere.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final int PAGE_SIZE = 10;     // pagination 단위

    private final CafeProductServiceImpl cafeProductService;
    private final CafeProductRepository cafeProductRepository;

    @Autowired
    public ProductController(CafeProductServiceImpl cafeProductService, CafeProductRepository cafeProductRepository) {
        this.cafeProductService = cafeProductService;
        this.cafeProductRepository = cafeProductRepository;
    }

    /* 상품 등록 */
    @PostMapping("/cafe/register")
    public ResponseEntity<JsonResponseDto> cafeRegist(@Valid @RequestBody CafeProductDto cafeProductDto) {
        // 필수 항목 검증
        cafeProductService.validProperty(cafeProductDto);
        // 상품 등록
        try {
            cafeProductService.registerItem(cafeProductDto);
        }catch (Exception e) {
            JsonConverter.toJsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.PRODUCT_REG_ERROR.getErrorMsg(), null);
        }

        return ResponseEntity.ok().body(JsonConverter.toOkNoDataJsonResponse());
    }

    /* 상품 수정
    * 과한 데이터 전송량일 수 있지만 우선 DTO 활용해서 간결하게 수정을 구현
    * */
    @PutMapping("/cafe/update/{id}")
    public ResponseEntity<JsonResponseDto> cafeUpdate(@PathVariable Long id, @Valid @RequestBody CafeProductDto cafeProductDto) {
        // 필수 항목 검증
        cafeProductService.validProperty(cafeProductDto);
        // 수정
        try {
            cafeProductService.updateProperty(id, cafeProductDto);
        }catch (Exception e) {
            JsonConverter.toJsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.PRODUCT_UPDATE_ERROR.getErrorMsg(), null);
        }

        return ResponseEntity.ok().body(JsonConverter.toOkNoDataJsonResponse());
    }

    /* 상품 삭제 */
    @DeleteMapping("/cafe/delete/{id}")
    public ResponseEntity<JsonResponseDto> cafeDelete(@PathVariable Long id) {
        // 삭제
        try {
            cafeProductService.removeItem(id);
        }catch (Exception e) {
            JsonConverter.toJsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.PRODUCT_DELETE_ERROR.getErrorMsg(), null);
        }

        return ResponseEntity.ok().body(JsonConverter.toOkNoDataJsonResponse());
    }

    @GetMapping("/cafe")
    public ResponseEntity<JsonResponseDto> getCafeProducts(@RequestParam(required = false) Long cursorId,
                                                     @RequestParam(required = false) Integer size) {
        if (size == null) size = PAGE_SIZE; // size의 Null 처리
        CursorResult<CafeProduct> list = cafeProductService.getItemList(cursorId, PageRequest.of(0, size));

        // DTO 형태로 변경해서 전송
        List<CafeProductDto> tempList = new ArrayList<>();

        list.getProducts().forEach(cafeProduct -> {
            tempList.add(CafeProductDto.fromEntity(cafeProduct));
        });

        CursorResult<CafeProductDto> convertList = new CursorResult<>(tempList,list.getHasNext());

        return ResponseEntity.ok().body(JsonConverter.toJsonResponse(HttpStatus.OK, "ok", convertList));
    }

    @GetMapping("/cafe/{id}")
    public ResponseEntity<JsonResponseDto> getCafeProductOne(@PathVariable Long id) {
        CafeProduct res = cafeProductService.getItem(id);

        if (res == null) {
            throw new CustomException(CustomErrorCode.PRODUCT_NO_ITEM_ERROR);
        }

        return ResponseEntity.ok().body(JsonConverter.toJsonResponse(HttpStatus.OK, "ok", CafeProductDto.fromEntity(res)));
    }
}
