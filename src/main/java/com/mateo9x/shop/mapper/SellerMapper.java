package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.Seller;
import com.mateo9x.shop.dto.SellerDTO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerMapper INSTANCE = Mappers.getMapper(SellerMapper.class);

    default Seller fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.setId(id);
        return seller;
    }

    Seller toEntity(SellerDTO sellerDTO);

    SellerDTO toDTO(Seller seller);

}
