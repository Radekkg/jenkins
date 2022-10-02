package com.capgemini.pdp.service;

import com.capgemini.pdp.dto.CredentialsDto;
import com.capgemini.pdp.dto.LoggedInUserDto;

public interface LoginService {

    LoggedInUserDto login (CredentialsDto credentialsDto);


}
