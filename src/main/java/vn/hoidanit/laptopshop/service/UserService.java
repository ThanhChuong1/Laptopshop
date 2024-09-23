package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello(){
        return "Hello from service";
    }

    public User handleSaveUser( User user){
        User saveUser = this.userRepository.save(user);
        return saveUser;
    }
    public List<User> getAllUser(){
        return this.userRepository.findAll();
    }
    public List<User> getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }
}
