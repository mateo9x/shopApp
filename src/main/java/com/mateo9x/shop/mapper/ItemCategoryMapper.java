package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.ItemCategory;
import com.mateo9x.shop.dto.ItemCategoryDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses= {})
public interface ItemCategoryMapper {

    @Mapping(source = "itemCategoryParentId", target = "itemCategoryParent")
    ItemCategory toEntity(ItemCategoryDTO itemCategoryDTO);

    @Mapping(source="itemCategory.id", target = "itemCategoryParentId")
    @Mapping(source="itemCategory.name", target = "itemCategoryParentName")
    ItemCategoryDTO toDTO(ItemCategory itemCategory);
    
    default ItemCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(id);
        return itemCategory;
    }

}
