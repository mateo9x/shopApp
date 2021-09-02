package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Item;
import com.mateo9x.shop.dto.ItemDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ItemMapper {

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }

    Item toEntity(ItemDTO itemDTO);

    ItemDTO toDTO(Item item);

}
