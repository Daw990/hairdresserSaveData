package com.dawid.hairdresserSaveData.services.implementation;

import com.dawid.hairdresserSaveData.entity.PriceList;
import com.dawid.hairdresserSaveData.entity.UserData;
import com.dawid.hairdresserSaveData.repository.UserDataRepository;
import com.dawid.hairdresserSaveData.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDataServiceImpl implements UserDataService {

    UserDataRepository userDataRepository;

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository){
        this.userDataRepository = userDataRepository;
    }


    @Override
    public UserData findById(Long id) {

        Optional<UserData> result = userDataRepository.findById(id);
        UserData userData = null;

        if (result.isPresent()) {
            userData = result.get();
        }
        else {
            throw new RuntimeException("Did not find UserData id - " + id);
        }
        return userData;
    }

    @Override
    public UserData save(UserData userData) {
        return userDataRepository.save(userData);
    }

    @Override
    public List<UserData> findByFirstName(String firstName) {
        return userDataRepository.findByFirstName(firstName);
    }
}
