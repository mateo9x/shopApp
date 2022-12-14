package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.domain.Seller;
import com.mateo9x.shop.dto.*;
import com.mateo9x.shop.mapper.SellerMapper;
import com.mateo9x.shop.repository.SellerRepository;
import com.mateo9x.shop.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final OrderPaymentService orderPaymentService;
    private final OrderAddressService orderAddressService;
    private final UserService userService;
    private final ItemService itemService;
    private final MailService mailService;


    @Override
    public SellerDTO getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        if (seller == null) {
            return null;
        }
        return sellerMapper.toDTO(seller);
    }

    @Override
    public void notifySellerAboutItemBuy(OrderDTO orderDTO) {
        OrderPaymentDTO orderPayment = orderPaymentService.findById(orderDTO.getOrderPaymentId());
        Seller seller = sellerRepository.findSellerByItemId(orderDTO.getItemId());
        UserDTO user = userService.findById(orderDTO.getUserId());
        ItemDTO item = itemService.findById(orderDTO.getItemId());
        OrderAddressDTO orderAddress = orderAddressService.findById(orderDTO.getOrderAddressId());
        String userFullname = prepareUserName(user);
        String orderAddressFullDataFormated = prepareUserContactData(orderAddress);
        String itemFullName = prepareFullItemName(item);
        String message = prepareMessage(userFullname, itemFullName, orderDTO.getAmountBought(), orderPayment.getType(), orderAddressFullDataFormated);
        mailService.notifySellerAboutHisItemProductBuy(message, seller.getMail());
    }

    private String prepareMessage(String userFullname, String itemFullName, Integer amountOfItems, String orderPaymentName, String orderAddress) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dzień dobry !\n\n")
                .append("Użytkownik ").append(userFullname).append(" zakupił w portalu \"Wszystko w sieci\" Twój produkt: ")
                .append(itemFullName).append(" w ilości: ").append(amountOfItems).append(" szt.")
                .append("\n")
                .append("Wybrał sposób dostawy: ").append(orderPaymentName)
                .append("\n")
                .append("Wysyłka na adres:")
                .append("\n")
                .append(orderAddress)
                .append("\n")
                .append("W razie problemów prosimy o kontakt z kupującym.")
                .append("\n\n")
                .append("Pozdrawiamy,")
                .append("\n")
                .append("Wszystko w sieci\nul. Programistów 3\n40-400 Warszawa\nKRS: XXXXXXXXXX");
        return sb.toString();
    }

    private String prepareUserName(UserDTO userDTO) {
        if (userDTO.getFirstName() != null && userDTO.getLastName() != null) {
            return userDTO.getFirstName() + " " + userDTO.getLastName() + " (" + userDTO.getUsername() + ")";
        }
        return userDTO.getUsername();
    }

    private String prepareFullItemName(ItemDTO itemDTO) {
        return itemDTO.getBrand() + " " + itemDTO.getModel();
    }

    private String prepareUserContactData(OrderAddressDTO orderAddressDTO) {
        StringBuilder sb = new StringBuilder();
        sb.append(orderAddressDTO.getFirstname()).append(" ").append(orderAddressDTO.getLastname())
                .append("\n")
                .append(orderAddressDTO.getMail())
                .append("\n")
                .append(orderAddressDTO.getStreet()).append(" ").append(orderAddressDTO.getStreetNumber())
                .append("\n")
                .append(orderAddressDTO.getPostalCode()).append(", ").append(orderAddressDTO.getCity())
                .append("\n")
                .append(orderAddressDTO.getPhoneNumber());
        return sb.toString();
    }
}
