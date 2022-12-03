package com.mateo9x.shop.serviceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.Item;
import com.mateo9x.shop.domain.User;
import com.mateo9x.shop.dto.ItemDTO;
import com.mateo9x.shop.mapper.ItemMapper;
import com.mateo9x.shop.repository.ItemRepository;
import com.mateo9x.shop.repository.UserRepository;
import com.mateo9x.shop.service.ItemService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;

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
    public List<ItemDTO> findAllFromCategory(Long id) {
        log.info("Request to find all Items by category {}: ", id);
        return itemRepository.findAllByItemCategoryId(id).stream().filter(dto -> !dto.isSold())
                .map(itemMapper::toDTO).collect(Collectors.toCollection(LinkedList::new));

    }

    @Override
    public List<ItemDTO> findAllBySellerId(Long id) {
        log.info("Request to find all Items by seller {}: ", id);
        return itemRepository.findBySellerId(id).stream().filter(dto -> !dto.isSold()).map(itemMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    @Override
    public List<ItemDTO> findAllByQuery(String query) {
        log.info("Request to find all Items by query {}: ", query);
        return itemRepository.findByBrandLikeOrModelLike(query).stream().filter(dto -> !dto.isSold()).map(itemMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    @Override
    public ItemDTO findById(Long id) {
        log.info("Request to find Item: {}", id);
        Item item = itemRepository.getById(id);
        return itemMapper.toDTO(item);
    }

    @Override
    public List<ItemDTO> findCartByUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (user.isPresent()) {
            log.info("Request to find Cart for User: {}", user.get().getId());
            return itemRepository.findByUserId(user.get().getId()).stream().map(itemMapper::toDTO)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        return null;
    }

    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        log.info("Request to save Item: {}", itemDTO);
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        return itemMapper.toDTO(item);
    }
}
