package vn.thanhchuong.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.thanhchuong.laptopshop.domain.Cart;
import vn.thanhchuong.laptopshop.domain.CartDetail;
import vn.thanhchuong.laptopshop.domain.Product;
import vn.thanhchuong.laptopshop.domain.User;
import vn.thanhchuong.laptopshop.repository.CartDetailRepository;
import vn.thanhchuong.laptopshop.repository.CartRepository;
import vn.thanhchuong.laptopshop.repository.ProductRepository;

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

     public void handleAddProductToCart(String email, long id, HttpSession session) {
          // check user đã có cart hay chưa ? nếu chưa -> tạo mới
          User user = this.userService.getUserByEmail(email);
          if (user != null) {
               // check user đã có cart hay chưa ? nếu chưa -> tạo mới
               Cart cart = this.cartRepository.findByUser(user);
               if (cart == null) {
                    // create card
                    Cart otherCart = new Cart();
                    otherCart.setUser(user);
                    otherCart.setSum(0);

                    cart = this.cartRepository.save(otherCart);
               }
               // save cart_detail
               // de lưu đc thì cần đối tượng product
               Optional<Product> productOptional = this.productRepository.findById(id);
               if (productOptional.isPresent()) {
                    Product reProduct = productOptional.get();
                    // check sản phẩm đươc thêm vào giỏ hàng hay chưa?
                    // boolean isExistProductInCart =
                    // this.cartDetailRepository.existsByCartAndProduct(cart, reProduct);
                    CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, reProduct);
                    if (oldDetail == null) {
                         CartDetail cartDetail = new CartDetail();
                         cartDetail.setCart(cart);
                         cartDetail.setProduct(reProduct);
                         cartDetail.setQuantity(1);
                         cartDetail.setPrice(reProduct.getPrice());
                         this.cartDetailRepository.save(cartDetail);

                         // update cart (sum)
                         int s = cart.getSum() + 1;
                         cart.setSum(s);
                         this.cartRepository.save(cart);
                         session.setAttribute("sum", s);
                    } else {
                         oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                         this.cartDetailRepository.save(oldDetail);
                    }

               }

          }
          // lưu cart_detail
     }

     public Cart fetchByUser(User user) {
          return this.cartRepository.findByUser(user);
     }

     public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
          Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
          if (cartDetailOptional.isPresent()) {
               CartDetail cartDetail = cartDetailOptional.get();
               Cart currentCart = cartDetail.getCart();
               // delete cart-detail
               this.cartDetailRepository.deleteById(cartDetailId);
               // update cart
               if (currentCart.getSum() > 1) {
                    // update currentCart
                    int s = currentCart.getSum() - 1;
                    currentCart.setSum(s);
                    session.setAttribute("sum", s);
                    this.cartRepository.save(currentCart);
               } else {
                    this.cartRepository.deleteById(currentCart.getId());
                    session.setAttribute("sum", 0);
               }
          }

     }
     public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails){
          for(CartDetail cartDetail : cartDetails){
               Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
               if(cdOptional.isPresent()){
                    CartDetail currentCartDetail =cdOptional.get();
                    currentCartDetail.setQuantity(cartDetail.getQuantity());
                    this.cartDetailRepository.save(currentCartDetail);
               }
          }
     }
}