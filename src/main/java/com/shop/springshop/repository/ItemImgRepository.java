package com.shop.springshop.repository;

import com.shop.springshop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdAsc(Long id);

    ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);
}
