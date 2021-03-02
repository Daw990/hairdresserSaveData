package com.dawid.hairdresserSaveData.services;

import com.dawid.hairdresserSaveData.entity.UserData;

import java.util.List;

public interface UserDataService {

    UserData findById(Long id);
    UserData save(UserData userData);
    List<UserData> findByFirstName(String firstName);
}
