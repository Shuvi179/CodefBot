package com.company.codef.information.service.impl;

import com.company.codef.information.entity.Chat;
import com.company.codef.information.entity.ConnUser;
import com.company.codef.information.repository.ChatRepository;
import com.company.codef.information.repository.ConnUserRepository;
import com.company.codef.information.service.ConnUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConnUserServiceImpl implements ConnUserService {

    @Autowired
    private ConnUserRepository connUserRepository;

    @Override
    public ConnUser addUser(String userName, String handle){
        ConnUser currentUser;
        currentUser = findByUserNameAndHandle(userName, handle);
        if(Objects.nonNull(currentUser)){
            return currentUser;
        }
        currentUser = createConnUserByValue(userName, handle);
        return connUserRepository.save(currentUser);
    }


    @Override
    public boolean updateUser(String userName, String handle){
        if(validateHandle(handle)){
            return false;
        }
        ConnUser currentUser = connUserRepository.findByUserName(userName);
        if(Objects.isNull(currentUser)){
            return false;
        }
        currentUser.setHandle(handle);
        connUserRepository.save(currentUser);
        return true;
    }

    @Override
    public ConnUser findByUserNameAndHandle(String userName, String handle) {
        return connUserRepository.findByUserNameAndHandle(userName, handle);
    }

    @Override
    public boolean validateConnUser(String userName, String handle) {
        return connUserRepository.existsByUserNameOrHandle(userName, handle);
    }

    private boolean validateHandle(String handle){
        return connUserRepository.existsByHandle(handle);
    }

    private ConnUser createConnUserByValue(String userName, String handle){
        return new ConnUser(userName, handle);
    }
}
