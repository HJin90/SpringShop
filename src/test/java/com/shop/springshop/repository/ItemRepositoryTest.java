package com.shop.springshop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.springshop.constant.ItemSellStatus;
import com.shop.springshop.entity.Item;
import com.shop.springshop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("Save Item Test")
    public void createItemTest() {
        Item item = new Item();
        item.setName("TestItem");
        item.setPrice(10000);
        item.setDetail("Test item detail");
        item.setStatus(ItemSellStatus.SELL);
        item.setStock(100);
        item.setRegisterTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println("savedItem = " + savedItem.toString());
    }

    public void createItemList() {
        for(int i = 1; i <= 10; i++){
            Item item = new Item();
            item.setName("TestItem" + i);
            item.setPrice(10000 + i);
            item.setDetail("Test item detail" + i);
            item.setStatus(ItemSellStatus.SELL);
            item.setStock(100);
            item.setRegisterTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);

        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByName("TestItem1");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());
        }
    }

    @Test
    @DisplayName("상품 조회 by 상품명 or 상품상세설명")
    public void findByItemNameOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByNameOrDetail("TestItem1","Test item detail5");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDescTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByDetail("item detail");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailNative(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByDetailByNative("item detail");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회테스트1")
    public void queryDslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.status.eq(ItemSellStatus.SELL))
                .where(qItem.detail.like("%"+"item detail"+"%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println("item = " + item.toString());
        }
    }

    public void createItemList2() {
        for(int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setName("테스트 상품" + i);
            item.setPrice(10000+i);
            item.setDetail("테스트 상품 상세 설명" + i);
            item.setStatus(ItemSellStatus.SELL);
            item.setStock(100);
            item.setRegisterTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6; i <= 10; i++){
            Item item = new Item();
            item.setName("테스트 상품" + i);
            item.setPrice(10000+i);
            item.setDetail("테스트 상품 상세 설명" + i);
            item.setStatus(ItemSellStatus.SOLD_OUT);
            item.setStock(0);
            item.setRegisterTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트2")
    public void queryDslTest2(){
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(item.detail.like("%"+itemDetail+"%"));
        booleanBuilder.and(item.price.gt(price));

        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            booleanBuilder.and(item.status.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements = " + itemPagingResult.getTotalElements());
        List<Item> resultItemList = itemPagingResult.getContent();
        for (Item item1 : resultItemList) {
            System.out.println("item1 = " + item1.toString());

        }

    }

}