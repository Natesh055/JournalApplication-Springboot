package com.natesing.journal.service;

import com.natesing.journal.entity.User;
import com.natesing.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    UserService userService;

    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public List<User> getAllEntries() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void deleteEntryById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

}
