package vn.thanhchuong.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import vn.thanhchuong.laptopshop.domain.Cart;
import vn.thanhchuong.laptopshop.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser(User user);
}
