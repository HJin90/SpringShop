package com.shop.springshop.dto;

import com.shop.springshop.constant.OrderStatus;
import com.shop.springshop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistoryDto {

    private Long orderId;

    private String orderDate;

    private OrderStatus orderStatus;

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public OrderHistoryDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
