package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User thanhchuong);

    List<User> findOneByEmail(String email);

    User getUserById(long id);

    void deleteById(User user);
    boolean existsByEmail(String email);
    User findByEmail(String email);

}
