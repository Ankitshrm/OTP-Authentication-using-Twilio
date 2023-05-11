package com.otp.services;

import com.otp.dao.UserRepository;
import com.otp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthDbService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        User newUser = userRepository.save(user);
        return newUser;
    }

    public void updateUser(User user ,int id) {
        user.setId(id);
        this.userRepository.save(user);

    }

    public void deleteUser(int id) {
        this.userRepository.deleteById(id);
    }

    public User getUserById(int id) {
        User newUser =null;
        try {
            newUser=(User) this.userRepository.findById(id);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }
}
