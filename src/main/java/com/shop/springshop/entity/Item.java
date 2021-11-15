package com.shop.springshop.entity;


import com.shop.springshop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {

    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name="price",nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    @Lob
    @Column(nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus status;

    private LocalDateTime registerTime;

    private LocalDateTime updateTime;

}
