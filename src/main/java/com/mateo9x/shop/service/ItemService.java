package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.ItemDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ItemService {

    ItemDTO save(ItemDTO itemDTO, List<MultipartFile> photos);

    List<ItemDTO> findAllFromCategory(Long id);

    List<ItemDTO> findAllBySellerId(Long id);

    List<ItemDTO> findAllByQuery(String query);

    ItemDTO findById(Long id);

    Boolean doesItemAlreadyExists(ItemDTO itemDTO);
}
