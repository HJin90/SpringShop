package com.shop.springshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private Integer price;
    private String detail;
    private String status;
    private LocalDateTime registerTime;
    private LocalDateTime updateTime;
}
