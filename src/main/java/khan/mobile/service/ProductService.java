package khan.mobile.service;

import khan.mobile.dto.ProductDto;
import khan.mobile.entity.Factories;
import khan.mobile.entity.Products;
import khan.mobile.entity.Users;
import khan.mobile.repository.FactoryRepository;
import khan.mobile.repository.ProductRepository;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final FactoryRepository factoryRepository;

    //상품 생성
    @Transactional
    public void createProduct(Long userId, ProductDto productDto) {
        //엔티티 조회
        validateUser(userId);

        //상품 생성
        /*
          TODO : 공장 값을 파라미터로 받아 저장하는 코드 필요
         */
        Products product = Products.builder()
                .productName(productDto.getName())
                .productColor(productDto.getColor())
                .productWeight(productDto.getWeight())
                .productSize(productDto.getSize())
                .productOther(productDto.getOther())
                .productImage(productDto.getImage())
                .user(Users.builder().userId(userId).build())
                .build();

        //상품 저장
        productRepository.save(product);
    }

    //상품 수정
    @Transactional
    public ProductDto updateProduct(Long userId, Long productId, ProductDto productDto) {
        //엔티티 조회
        validateUser(userId);
        Products findProduct = validateProduct(productId);

        //상품 수정
        findProduct.updateProduct(
                productDto.getName(),
                productDto.getColor(),
                productDto.getSize(),
                productDto.getWeight(),
                productDto.getOther()
        );

        return ProductDto.productDto(findProduct);

    }

    //상품 출력
    public Page<ProductDto> getProductList(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductDto::productDto);
    }

    //상품 삭제
    public void deleteProduct(Long userId, Long productId, ProductDto productDto) {

    }

    // 상품 검색
    public Page<ProductDto> getSearchProductList(String productName, Pageable pageable) {

        Page<Products> result = productRepository.findByProductNameContaining(productName, pageable);

        return result.map(ProductDto::productDto);
    }

    //상품 상세
    public ProductDto getProductDetail(Long product_id) {
//        validateUser(user_id);
        Products findProduct = validateProduct(product_id);

        return ProductDto.builder()
                .id(findProduct.getProductId())
                .name(findProduct.getProductName())
                .color(findProduct.getProductColor())
                .size(findProduct.getProductSize())
                .weight(findProduct.getProductWeight())
                .other(findProduct.getProductOther())
                .image(findProduct.getProductImage())
                .build();

    }

    private Products validateProduct(Long product_id) {
        return productRepository.findById(product_id).orElseThrow(() -> new IllegalArgumentException("일치하는 상품 정보가 없습니다"));
    }

    private Factories validateFactory(Long factory_id) {
        return factoryRepository.findById(factory_id).orElseThrow(() -> new IllegalArgumentException("일치하는 공장 정보가 없습니다"));
    }

    private Users validateUser(Long user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("일치하는 유저 정보가 없습니다"));
    }

}
