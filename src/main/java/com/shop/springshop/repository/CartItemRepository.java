package com.shop.springshop.repository;

import com.shop.springshop.dto.CartDetailDto;
import com.shop.springshop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.shop.springshop.dto.CartDetailDto(ci.id, i.name, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i "+
            "where ci.cart.id = :cartId "+ "and im.item.id = ci.item.id " + "and im.repImgYn = 'Y'" +
            "order by ci.registerTime desc ")
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
