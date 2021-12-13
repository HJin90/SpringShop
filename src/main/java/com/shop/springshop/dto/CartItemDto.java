package com.shop.springshop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CartItemDto {

    @NotNull(message = "상품을 선택해 주세요.")
    private Long itemId;

    @Min(value = 1, message = "최고 1개 이상 담아주세요")
    private int count;
}
