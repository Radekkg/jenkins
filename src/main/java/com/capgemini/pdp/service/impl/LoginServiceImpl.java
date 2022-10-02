package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.dto.CredentialsDto;
import com.capgemini.pdp.dto.LoggedInUserDto;
import com.capgemini.pdp.repository.UserRepository;
import com.capgemini.pdp.service.LoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoggedInUserDto login(CredentialsDto credentialsDto) {
        String email = credentialsDto.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            return null;
        }
        LoggedInUserDto loggedInUserDto = new LoggedInUserDto();
        loggedInUserDto.setId(userEntity.getId());
        loggedInUserDto.setRole(userEntity.getRoleName().toString());
        loggedInUserDto.setPassword(userEntity.getPassword());

        return loggedInUserDto;
    }
}
