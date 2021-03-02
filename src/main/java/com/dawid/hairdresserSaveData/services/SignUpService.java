package com.dawid.hairdresserSaveData.services;

import com.dawid.hairdresserSaveData.entity.Role;
import com.dawid.hairdresserSaveData.entity.User;
import com.dawid.hairdresserSaveData.entity.UserData;

public interface SignUpService {

    void signUpUser(User user, UserData userData);
    User findById(Long id);
    User save(User user);
    User findByUserData(UserData userData);

}
