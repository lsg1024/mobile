package khan.mobile.service;

import khan.mobile.dto.ProductOrderDto;
import khan.mobile.dto.ProductOrderItemDto;
import khan.mobile.entity.*;
import khan.mobile.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;


    // 제품 주문
    @Transactional
    public void createOrder(Long user_id, Long store_id, ProductOrderDto productOrderDto) {
        //관련 엔티티 조회
        Users user = validateUser(user_id);
        Stores store = validateStore(store_id);

        // 주문 엔티티 생성
        Product_order order = Product_order.builder()
                .user(user)
                .stores(store)
                .build();

        createOrderItems(productOrderDto, order);

        productOrderRepository.save(order);

    }

    // 제품 주문 정보 수정
    @Transactional
    public void updateOrder(Long user_id, Long orderItems_id, ProductOrderItemDto productOrderItemDto) {
        //엔티티 조회
        Product_orderItem findItem = validateOrderItem(orderItems_id);
        Users user = validateUser(user_id);
        log.info("주문 ID 정보: {}", findItem.getProduct_orderItem_id());

        // 사용자 권한 확인 및 주문 수정 권한 검증
        Check_permissions(user, findItem, user_id, "주문을 수정할 권한이 없습니다.");

        //주문 정보 수정
        findItem.updateOrderItem(
                productOrderItemDto.getColor(),
                productOrderItemDto.getSize(),
                productOrderItemDto.getOther(),
                productOrderItemDto.getQuantity()
        );

        // 관리자 권한으로 수정된 경우 로그 기록
        EditByAdmin(user, "admin 권한으로 주문 정보를 수정했습니다. 주문 ID: {}, 사용자 ID: {}", orderItems_id, user_id);

    }


    @Transactional
    public void deleteOrder(Long user_id, Long orderItems_id) {
        //엔티티 조회
        Product_orderItem findItem = validateOrderItem(orderItems_id);
        Users user = validateUser(user_id);

        // 사용자 권한 확인 및 주문 수정 권한 검증
        Check_permissions(user, findItem, user_id, "주문을 삭제할 권한이 없습니다.");

        productOrderItemRepository.delete(findItem);

        // 관리자 권한으로 수정된 경우 로그 기록
        EditByAdmin(user, "admin 권한으로 주문 정보를 삭제했습니다. 주문 ID: {}, 사용자 ID: {}", orderItems_id, user_id);
    }

    // 주문 상품 목록
    public List<Product_orderItem> getUserOrderItems(Long userId) {
        // 사용자가 한 모든 주문 조회
        List<Product_order> orders = productOrderRepository.findByUserId(userId);

        // 주문 ID 목록 추출
        List<Long> orderIds = orders.stream()
                .map(Product_order::getProduct_order_id)
                .collect(Collectors.toList());

        // 주문 ID 목록에 해당하는 모든 주문 항목 조회
        return productOrderItemRepository.findByProductOrderIdIn(orderIds);
    }

    private void EditByAdmin(Users user, String format, Long orderItems_id, Long user_id) {
        if (user.getRole() == Role.ADMIN) {
            log.warn(format, orderItems_id, user_id);
        }
    }

    private void Check_permissions(Users user, Product_orderItem findItem, Long user_id, String s) {
        if (!(user.getRole() == Role.ADMIN) && !findItem.getProduct_order().getUser().getUser_id().equals(user_id)) {
            throw new IllegalArgumentException(s);
        }
    }
    private Product_orderItem validateOrderItem(Long orderItems_id) {
        return productOrderItemRepository.findById(orderItems_id).orElseThrow(() -> new IllegalArgumentException("주문 번호를 찾을 수 없습니다"));
    }

    private Stores validateStore(Long store_id) {
        return storeRepository.findById(store_id).orElseThrow(() -> new IllegalArgumentException("일치하는 상점 정보가 없습니다"));
    }

    private Users validateUser(Long user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("일치하는 유저 정보가 없습니다"));
    }

    
    private void createOrderItems(ProductOrderDto productOrderDto, Product_order order) {
        for (ProductOrderItemDto itemDto : productOrderDto.getOrderItems()) {
            log.info("제품 ID 정보: {}", itemDto.getProduct_id());

            Products product = productRepository.findById(itemDto.getProduct_id()).orElseThrow(() -> new IllegalArgumentException("일치하는 제품 정보가 없습니다"));

            Product_orderItem orderItem = Product_orderItem.builder()
                    .product_orderItem_color(itemDto.getColor())
                    .product_orderItem_size(itemDto.getSize())
                    .product_orderItem_other(itemDto.getOther())
                    .product_orderItem_quantity(itemDto.getQuantity())
                    .products(product)
                    .product_order(order)
                    .build();

            productOrderItemRepository.save(orderItem);
        }
    }
}
