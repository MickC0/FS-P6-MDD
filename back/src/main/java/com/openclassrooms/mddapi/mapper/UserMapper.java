package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CreateUserRequest;
import com.openclassrooms.mddapi.dto.ModifyUserRequest;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRoles(),
                entity.getSubscriptions()
        );
    }

    public UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setRoles(dto.roles());
        entity.setSubscriptions(dto.subscriptions());
        return entity;
    }


    public UserEntity fromCreateRequest(CreateUserRequest req) {
        UserEntity u = new UserEntity();
        u.setName(req.name());
        u.setEmail(req.email());
        u.setPassword(req.password());
        return u;
    }


    public void updateFromModifyRequest(UserEntity u, ModifyUserRequest req) {
        if (req.name() != null)      u.setName(req.name());
        if (req.email() != null)     u.setEmail(req.email());
        if (req.password() != null && !req.password().isBlank()) {
            u.setPassword(req.password());
        }

    }
}
