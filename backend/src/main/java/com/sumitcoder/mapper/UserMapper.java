package com.sumitcoder.mapper;

import com.sumitcoder.dto.OrderDto;
import com.sumitcoder.dto.OrderItemDto;
import com.sumitcoder.dto.UserDto;
import com.sumitcoder.model.Order;
import com.sumitcoder.model.OrderItem;
import com.sumitcoder.model.User;

public class UserMapper {

    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
