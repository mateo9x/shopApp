package com.mateo9x.shop.serviceImpl;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.ItemCategory;
import com.mateo9x.shop.dto.ItemCategoryDTO;
import com.mateo9x.shop.mapper.ItemCategoryMapper;
import com.mateo9x.shop.repository.ItemCategoryRepository;
import com.mateo9x.shop.service.ItemCategoryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemCategoryMapper itemCategoryMapper;

    @Override
    public void deleteItemCategory(Long id) {
        log.info("Request to delete ItemCategory: {}", id);
        ItemCategory item = itemCategoryRepository.getById(id);
        itemCategoryRepository.delete(item);
    }

    @Override
    public List<ItemCategoryDTO> findAll() {
        log.info("Request to find all ItemCategories: ");
        return itemCategoryRepository.findAll().stream()
                .map(itemCategoryMapper::toDTO)
                .sorted(Comparator.comparing(ItemCategoryDTO::getName))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public ItemCategoryDTO findById(Long id) {
        log.info("Request to find ItemCategory: {}", id);
        ItemCategory item = itemCategoryRepository.getById(id);
        return itemCategoryMapper.toDTO(item);
    }

    @Override
    public ItemCategoryDTO save(ItemCategoryDTO itemDTO) {
        log.info("Request to save ItemCategory: {}", itemDTO);
        ItemCategory item = itemCategoryMapper.toEntity(itemDTO);
        item = itemCategoryRepository.save(item);
        return itemCategoryMapper.toDTO(item);
    }
}
