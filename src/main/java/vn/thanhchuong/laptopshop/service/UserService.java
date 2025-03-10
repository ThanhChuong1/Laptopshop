package vn.thanhchuong.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.thanhchuong.laptopshop.domain.Role;
import vn.thanhchuong.laptopshop.domain.User;
import vn.thanhchuong.laptopshop.domain.DTO.RegisterDTO;
import vn.thanhchuong.laptopshop.repository.RoleRepository;
import vn.thanhchuong.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    public User handleSaveUser(User user) {
        User saveUser = this.userRepository.save(user);
        return saveUser;
    }

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User getuserbyId(Long id) {
        User users = this.userRepository.getUserById(id);
        return users;

    }

    public void deleUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        return user;
    }

    // check Email exist
    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
