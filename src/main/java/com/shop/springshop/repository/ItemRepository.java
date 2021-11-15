package com.shop.springshop.repository;

import com.shop.springshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    List<Item> findByName(String name);

    List<Item> findByNameOrDetail(String name, String detail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.detail like %:itemDetail% order by i.price desc")
    List<Item> findByDetail(@Param("itemDetail") String detail);

    @Query(value = "select * from item i where i.detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByDetailByNative(@Param("itemDetail") String detail);

}
