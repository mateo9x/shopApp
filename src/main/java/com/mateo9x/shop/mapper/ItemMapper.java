package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Item;
import com.mateo9x.shop.dto.ItemDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses= {ItemCategoryMapper.class, SellerMapper.class})
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(source = "itemCategoryId", target = "itemCategory")
    @Mapping(source = "sellerId", target = "seller")
    Item toEntity(ItemDTO itemDTO);

    @Mapping(source = "itemCategory.id", target = "itemCategoryId")
    @Mapping(source = "itemCategory.name", target = "itemCategoryName")
    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "seller.name", target = "sellerName")
    ItemDTO toDTO(Item item);

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }

}
