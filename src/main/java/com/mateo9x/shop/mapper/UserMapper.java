package com.mateo9x.shop.mapper;

import com.mateo9x.shop.domain.User;
import com.mateo9x.shop.dto.UserDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses= {})
public interface UserMapper {

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);

}
