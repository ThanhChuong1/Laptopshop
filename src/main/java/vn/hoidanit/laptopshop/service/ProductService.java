package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
     private final ProductRepository productRepository;
     private final CartDetailRepository cartDetailRepository;
     private final CartRepository cartRepository;
     private final UserService userService;

     public ProductService(ProductRepository productRepository, CartRepository cartRepository,
               CartDetailRepository cartDetailRepository, UserService userService) {
          this.productRepository = productRepository;
          this.cartDetailRepository = cartDetailRepository;
          this.cartRepository = cartRepository;
          this.userService = userService;
     }

     public Product handleSaveProduct(Product product) {
          return this.productRepository.save(product);
     }

     public List<Product> getAllProducts() {
          return this.productRepository.findAll();
     }

     public Optional<Product> getProductByid(long id) {
          return this.productRepository.findById(id);
     }

     public void deleteProduct(long id) {
          this.productRepository.deleteById(id);
     }

     public void handleAddProductToCart(String email, long id) {
          // check user đã có cart hay chưa ? nếu chưa -> tạo mới
          User user = this.userService.getUserByEmail(email);
          if (user != null) {
               // check user đã có cart hay chưa ? nếu chưa -> tạo mới
               Cart cart = this.cartRepository.findByUser(user);
               if (cart == null) {
                    // create card
                    Cart otherCart = new Cart();
                    otherCart.setUser(user);
                    otherCart.setSum(1);

                    cart = this.cartRepository.save(otherCart);
               }
               // save cart_detail
               // de lưu đc thì cần đối tượng product
               Optional<Product> productOptional = this.productRepository.findById(id);
               if (productOptional.isPresent()) {
                    Product reProduct = productOptional.get();

                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(reProduct);
                    cartDetail.setQuantity(1);
                    cartDetail.setPrice(reProduct.getPrice());
                    this.cartDetailRepository.save(cartDetail);
               }

          }
          // lưu cart_detail
     }
}