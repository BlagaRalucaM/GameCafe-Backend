package com.example.gameCafe.services;

import com.example.gameCafe.entities.User;
import com.example.gameCafe.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    public User saveUser( @Valid User newUser) {
        return userRepository.save(newUser);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUserById(Integer id, User newUser) throws Exception {
        if (newUser != null) {
            User replacedUser = userRepository.findUserById(id);
            if (replacedUser != null) {
                replacedUser.setFirstName(newUser.getFirstName());
                replacedUser.setLastName(newUser.getLastName());
                replacedUser.setEmail(newUser.getEmail());
                replacedUser.setPhoneNr(newUser.getPhoneNr());
                replacedUser.setBirthday(newUser.getBirthday());
                return userRepository.save(replacedUser);
            } else throw new Exception("Replaced User not found");
        }
        throw new Exception("New User not found");
    }

    public User updateEmail(Integer id, String newEmail) throws Exception {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setEmail(newEmail);
            return userRepository.save(user);
        }
        throw new Exception("User not found");
    }

}
