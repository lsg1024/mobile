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
    public void createProduct(Long user_id, ProductDto productDto) {
        //엔티티 조회
        validateUser(user_id);

        //상품 생성
        /*
          TODO : 공장 값을 파라미터로 받아 저장하는 코드 필요
         */
        Products product = Products.builder()
                .productName(productDto.getName())
                .product_color(productDto.getColor())
                .product_weight(productDto.getWeight())
                .product_size(productDto.getSize())
                .product_other(productDto.getOther())
                .product_image(productDto.getImage())
                .user(Users.builder().user_id(user_id).build())
                .build();

        //상품 저장
        productRepository.save(product);
    }

    //상품 수정
    @Transactional
    public ProductDto updateProduct(Long user_id, Long product_id, ProductDto productDto) {
        //엔티티 조회
        validateUser(user_id);
        Products findProduct = validateProduct(product_id);

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
    public void deleteProduct(Long user_id, Long product_id, ProductDto productDto) {

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
                .id(findProduct.getProduct_id())
                .name(findProduct.getProductName())
                .color(findProduct.getProduct_color())
                .size(findProduct.getProduct_size())
                .weight(findProduct.getProduct_weight())
                .other(findProduct.getProduct_other())
                .image(findProduct.getProduct_image())
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
