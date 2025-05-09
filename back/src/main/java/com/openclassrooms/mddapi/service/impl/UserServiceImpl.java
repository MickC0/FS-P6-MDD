package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.CreateUserRequest;
import com.openclassrooms.mddapi.dto.ModifyUserRequest;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.TopicEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository   repository;
    private final UserMapper       mapper;
    private final PasswordEncoder  encoder;

    public UserServiceImpl(
            UserRepository repository,
            UserMapper     mapper,
            PasswordEncoder encoder
    ) {
        this.repository = repository;
        this.mapper     = mapper;
        this.encoder    = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity u = repository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé : " + username));
        return User.builder()
                .username(u.getEmail())
                .password(u.getPassword())
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(u.getRoles()))
                .build();
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        UserEntity u = mapper.fromCreateRequest(request);

        if (repository.findByEmail(u.getEmail()).isPresent()) {
            throw new BadCredentialsException("Email déjà utilisé : " + u.getEmail());
        }

        u.setPassword(encoder.encode(u.getPassword()));
        u.setRoles("ROLE_USER");

        UserEntity saved = repository.save(u);
        return mapper.toDto(saved);
    }

    @Override
    public UserDto getUserProfile(String username) {
        UserEntity u = repository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé : " + username));
        return mapper.toDto(u);
    }

    @Override
    public UserDto modifyUser(String username, ModifyUserRequest request) {
        UserEntity u = repository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé : " + username));

        if (!encoder.matches(request.oldPassword(), u.getPassword())) {
            throw new BadCredentialsException("Mot de passe courant invalide");
        }

        mapper.updateFromModifyRequest(u, request);

        if (request.password() != null && !request.password().isBlank()) {
            u.setPassword(encoder.encode(u.getPassword()));
        }

        UserEntity updated = repository.save(u);
        return mapper.toDto(updated);
    }

    @Override
    public UserDto updateSubscriptions(String username, Set<TopicEntity> subscriptions) {
        UserEntity u = repository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé : " + username));
        u.setSubscriptions(subscriptions);
        UserEntity saved = repository.save(u);
        return mapper.toDto(saved);
    }
}
