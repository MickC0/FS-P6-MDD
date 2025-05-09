package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CreateUserRequest;
import com.openclassrooms.mddapi.dto.ModifyUserRequest;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.TopicEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {


    @Override
    UserDetails loadUserByUsername(String username) throws EntityNotFoundException;


    UserDto createUser(CreateUserRequest request) throws BadCredentialsException;


    UserDto getUserProfile(String username) throws EntityNotFoundException;


    UserDto modifyUser(String username, ModifyUserRequest request)
            throws BadCredentialsException, EntityNotFoundException;


    UserDto updateSubscriptions(String username, Set<TopicEntity> subscriptions);
}
