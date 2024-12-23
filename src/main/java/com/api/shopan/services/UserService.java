package com.api.shopan.services;

import com.api.shopan.dtos.UserDTO;
import com.api.shopan.entities.User;
import com.api.shopan.enums.RoleEnum;
import com.api.shopan.repositories.RoleRepository;
import com.api.shopan.repositories.UserRepository;
import com.api.shopan.utils.ResponseUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Map<String, String> saveUser(UserDTO userDTO) {
        try {
            if (userRepository.findByEmail(userDTO.getEmail()) != null) {
                return ResponseUtils.makeMessageWithCode("Usuário já existe no sistema", Integer.toString(HttpStatus.FORBIDDEN.value()));
            }
            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user.setActive(true);
            user.setRole(roleRepository.findOne(RoleEnum.GENERAL.getValue()));

            userRepository.save(user);

            return ResponseUtils.makeMessageWithCode("Usuário cadastrado com sucesso", Integer.toString(HttpStatus.ACCEPTED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}