package com.mateo9x.shop.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "items_category")
public class ItemCategory implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "item_id")
    private ItemCategory itemCategoryParent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCategory getItemCategoryParent() {
        return itemCategoryParent;
    }

    public void setItemCategoryParent(ItemCategory itemCategoryParent) {
        this.itemCategoryParent = itemCategoryParent;
    }

}