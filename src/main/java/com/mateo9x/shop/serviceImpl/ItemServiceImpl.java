package com.mateo9x.shop.serviceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.Item;
import com.mateo9x.shop.dto.ItemDTO;
import com.mateo9x.shop.mapper.ItemMapper;
import com.mateo9x.shop.repository.ItemRepository;
import com.mateo9x.shop.service.ItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public void deleteItem(Long id) {
        log.info("Request to delete Item: {}", id);
        Item item = itemRepository.getById(id);
        itemRepository.delete(item);
    }

    @Override
    public List<ItemDTO> findAll() {
        log.info("Request to find all Items: ");
        return itemRepository.findAll().stream().map(itemMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public ItemDTO findById(Long id) {
        log.info("Request to find Item: {}", id);
        Item item = itemRepository.getById(id);
        return itemMapper.toDTO(item);
    }

    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        log.info("Request to save Item: {}", itemDTO);
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        return itemMapper.toDTO(item);
    }

}
