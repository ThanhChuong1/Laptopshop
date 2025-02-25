package vn.thanhchuong.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.thanhchuong.laptopshop.domain.Cart;
import vn.thanhchuong.laptopshop.domain.CartDetail;
import vn.thanhchuong.laptopshop.domain.Product;
import vn.thanhchuong.laptopshop.domain.User;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    boolean existsByCartAndProduct(Cart cart, Product product);

    CartDetail findByCartAndProduct(Cart cart, Product product);
}
