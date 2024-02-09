package khan.mobile.service;

import khan.mobile.dto.ProductOrderDto;
import khan.mobile.dto.ProductOrderItemDetailDto;
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
    public void createOrder(Long userId, Long storeId, ProductOrderDto productOrderDto) {
        //관련 엔티티 조회
        Users user = validateUser(userId);
        Stores store = validateStore(storeId);

        // 주문 엔티티 생성
        ProductOrder order = ProductOrder.builder()
                .user(user)
                .stores(store)
                .build();

        createOrderItems(productOrderDto, order);

        productOrderRepository.save(order);

    }

    // 제품 주문 정보 수정
    @Transactional
    public void updateOrder(Long userId, Long orderItemsId, ProductOrderItemDto productOrderItemDto) {
        //엔티티 조회
        ProductOrderItem findItem = validateOrderItem(orderItemsId);
        Users user = validateUser(userId);
        log.info("주문 ID 정보: {}", findItem.getProductOrderItemId());

        // 사용자 권한 확인 및 주문 수정 권한 검증
        Check_permissions(user, findItem, userId, "주문을 수정할 권한이 없습니다.");

        //주문 정보 수정
        findItem.updateOrderItem(
                productOrderItemDto.getColor(),
                productOrderItemDto.getSize(),
                productOrderItemDto.getOther(),
                productOrderItemDto.getQuantity()
        );

        // 관리자 권한으로 수정된 경우 로그 기록
        EditByAdmin(user, "admin 권한으로 주문 정보를 수정했습니다. 주문 ID: {}, 사용자 ID: {}", orderItemsId, userId);

    }


    @Transactional
    public void deleteOrder(Long userId, Long orderItemsId) {
        //엔티티 조회
        ProductOrderItem findItem = validateOrderItem(orderItemsId);
        Users user = validateUser(userId);

        // 사용자 권한 확인 및 주문 수정 권한 검증
        Check_permissions(user, findItem, userId, "주문을 삭제할 권한이 없습니다.");

        productOrderItemRepository.delete(findItem);

        // 관리자 권한으로 수정된 경우 로그 기록
        EditByAdmin(user, "admin 권한으로 주문 정보를 삭제했습니다. 주문 ID: {}, 사용자 ID: {}", orderItemsId, userId);
    }

    // 주문 항목 상세 조회
    public List<ProductOrderItemDetailDto> getOrderItemListDetails(Long userId) {
        // 사용자가 한 모든 주문 조회
        List<ProductOrder> orders = productOrderRepository.findByUserId(userId);

        // 주문 ID 목록 추출
        List<Long> orderIds = orders.stream()
                .map(ProductOrder::getProductOrderId)
                .collect(Collectors.toList());

        // 주문 항목 상세 조회
        return productOrderItemRepository.findOrderItemDetail(orderIds);
    }

    private void EditByAdmin(Users user, String format, Long orderItemsId, Long userId) {
        if (user.getRole() == Role.ADMIN) {
            log.warn(format, orderItemsId, userId);
        }
    }

    private void Check_permissions(Users user, ProductOrderItem findItem, Long userId, String s) {
        if (!(user.getRole() == Role.ADMIN) && !findItem.getProductOrder().getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException(s);
        }
    }
    private ProductOrderItem validateOrderItem(Long orderItemsId) {
        return productOrderItemRepository.findById(orderItemsId).orElseThrow(() -> new IllegalArgumentException("주문 번호를 찾을 수 없습니다"));
    }

    private Stores validateStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("일치하는 상점 정보가 없습니다"));
    }

    private Users validateUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("일치하는 유저 정보가 없습니다"));
    }

    
    private void createOrderItems(ProductOrderDto productOrderDto, ProductOrder order) {
        for (ProductOrderItemDto itemDto : productOrderDto.getOrderItems()) {
            log.info("제품 ID 정보: {}", itemDto.getProductId());

            Products product = productRepository.findById(itemDto.getProductId()).orElseThrow(() -> new IllegalArgumentException("일치하는 제품 정보가 없습니다"));

            ProductOrderItem orderItem = ProductOrderItem.builder()
                    .productOrderItemColor(itemDto.getColor())
                    .productOrderItemSize(itemDto.getSize())
                    .productOrderItemOther(itemDto.getOther())
                    .productOrderItemQuantity(itemDto.getQuantity())
                    .products(product)
                    .productOrder(order)
                    .build();

            productOrderItemRepository.save(orderItem);
        }
    }
}
