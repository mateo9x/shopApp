package com.mateo9x.shop.serviceImpl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.Item;
import com.mateo9x.shop.dto.ItemDTO;
import com.mateo9x.shop.mapper.ItemMapper;
import com.mateo9x.shop.repository.ItemRepository;
import com.mateo9x.shop.service.ItemService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final PhotoServiceImpl photoService;

    @Override
    public List<ItemDTO> findAllFromCategory(Long id) {
        log.info("Request to find all Items by category {}: ", id);
        List<ItemDTO> itemDTOS = itemRepository.findAllByItemCategoryId(id).stream().filter(dto -> dto.getAmountAvailable() > 0)
                .map(itemMapper::toDTO).collect(Collectors.toCollection(LinkedList::new));
        itemDTOS.forEach(this::fillPhotosForItem);
        return itemDTOS;
    }

    @Override
    public List<ItemDTO> findAllBySellerIdActive(Long id) {
        log.info("Request to find all Items active by seller {}: ", id);
        List<ItemDTO> itemDTOS = itemRepository.findBySellerId(id).stream().filter(dto -> dto.getAmountAvailable() > 0).map(itemMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
        itemDTOS.forEach(this::fillPhotosForItem);
        return itemDTOS;
    }

    @Override
    public List<ItemDTO> findAllByQuery(String query) {
        log.info("Request to find all Items by query: {}", query);
        List<ItemDTO> itemDTOS = itemRepository.findByBrandLikeOrModelLike(query).stream().filter(dto -> dto.getAmountAvailable() > 0).map(itemMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
        itemDTOS.forEach(this::fillPhotosForItem);
        return itemDTOS;
    }

    @Override
    public ItemDTO findById(Long id) {
        log.info("Request to find Item: {}", id);
        Item item = itemRepository.getById(id);
        ItemDTO itemDTO = itemMapper.toDTO(item);
        fillPhotosForItem(itemDTO);
        return itemDTO;
    }

    @Override
    public ItemDTO save(ItemDTO itemDTO, List<MultipartFile> photos) {
        log.info("Request to save Item: {}", itemDTO);
        itemDTO.setCreateDate(LocalDateTime.now());
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        if (CollectionUtils.isNotEmpty(photos)) {
            Long itemId = item.getId();
            photos.forEach(photo -> photoService.saveMultipartFileInResourceFolder(itemId.toString(), photo));
        }
        return itemMapper.toDTO(item);
    }

    @Override
    public Boolean doesItemAlreadyExists(ItemDTO itemDTO) {
        Optional<Item> item = itemRepository.doesItemAlreadyExists(itemDTO.getBrand(), itemDTO.getModel(), itemDTO.getPrice(), itemDTO.getSellerId(), itemDTO.getItemCategoryId());
        return item.isPresent();
    }

    private void fillPhotosForItem(ItemDTO itemDTO) {
        if (!itemDTO.getPhotoUrl().equals("-")) {
            if (itemDTO.getPhotoUrl().contains(";")) {
                List<String> fileNames = asList(itemDTO.getPhotoUrl().split(";"));
                List<byte[]> photoFiles = new ArrayList<>();
                fileNames.forEach(fileName -> photoFiles.add(photoService.getPhotoFromResourceFolder(itemDTO.getId().toString(), fileName)));
                itemDTO.setPhotoFiles(photoFiles);
            } else {
                itemDTO.setPhotoFiles(singletonList(photoService.getPhotoFromResourceFolder(itemDTO.getId().toString(), itemDTO.getPhotoUrl())));
            }
        }
        itemDTO.setPhotoFiles(itemDTO.getPhotoFiles().stream().filter(Objects::nonNull).collect(Collectors.toList()));
    }
}
