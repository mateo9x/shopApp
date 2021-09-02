package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.ItemCategory;
import com.mateo9x.shop.dto.ItemCategoryDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses= {})
public interface ItemCategoryMapper {
    
    
    default ItemCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(id);
        return itemCategory;
    }

    ItemCategory toEntity(ItemCategoryDTO itemCategoryDTO);

    ItemCategoryDTO toDTO(ItemCategory itemCategory);

}
