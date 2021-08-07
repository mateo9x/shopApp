package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.User;
import com.mateo9x.shop.dto.UserDTO;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toUser(UserDTO userDTO);

    UserDTO toDTO(User user);

}
