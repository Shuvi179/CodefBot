package com.company.codef.information.service;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ConnUser;

import java.util.List;

public interface ConnUserService {
    ConnUser addUser(String userName, String handle);
    boolean validateConnUser(String userName, String handle);
    boolean updateUser(String userName, String handle);
    ConnUser findByUserNameAndHandle(String userName, String handle);
}
