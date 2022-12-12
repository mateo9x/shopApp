package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.domain.Seller;
import com.mateo9x.shop.dto.SellerDTO;
import com.mateo9x.shop.mapper.SellerMapper;
import com.mateo9x.shop.repository.SellerRepository;
import com.mateo9x.shop.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    @Override
    public SellerDTO getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        if (seller == null) {
            return null;
        }
        return sellerMapper.toDTO(seller);
    }
}
