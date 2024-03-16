package khan.mobile.service;

import khan.mobile.dto.ProductDto;
import khan.mobile.entity.Factories;
import khan.mobile.entity.ProductImage;
import khan.mobile.entity.Products;
import khan.mobile.entity.Users;
import khan.mobile.repository.FactoryRepository;
import khan.mobile.repository.ProductRepository;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final FactoryRepository factoryRepository;

    //상품 생성
    @Transactional
    public void createProduct(Long userId, ProductDto.Create productDto) {

        Products product = Products.builder()
                .productName(productDto.getName())
                .productColor(productDto.getColor())
                .productWeight(productDto.getWeight())
                .productSize(productDto.getSize())
                .productOther(productDto.getOther())
                .productImage(productDto.getImages())
                .user(Users.builder().userId(userId).build())
                .build();

        //상품 저장
        productRepository.save(product);
    }

    //상품 수정
    @Transactional
    public void updateProduct(Long userId, Long productId, ProductDto.Create productDto) {

        log.info("userId = {}", userId);
        log.info("productId = {}", productId);
        Products findProduct = validateProduct(productId);
        if (findProduct != null) {
            //상품 수정
            findProduct.updateProduct(
                    productDto.getName(),
                    productDto.getColor(),
                    productDto.getSize(),
                    productDto.getWeight(),
                    productDto.getOther()
            );
        } else {
            throw new IllegalArgumentException("일치하는 상품 정보가 없음");
        }
    }

    //상품 출력
    public Page<ProductDto.ProductDataSet> getProductList(ProductDto.ProductCondition condition, Pageable pageable) {
        return productRepository.findProductPageable(condition, pageable);
    }

    //상품 삭제
    public void deleteProduct(Long userId, Long productId, ProductDto productDto) {

    }

    //상품 상세
    public ProductDto.Detail getProductDetail(Long product_id) {

        ProductDto.Detail productDto =  productRepository.findProductDetail(product_id);

        log.info("productDto = {}", productDto);

        return productDto;

    }

    private Products validateProduct(Long product_id) {
        return productRepository.findById(product_id).orElseThrow(() -> new IllegalArgumentException("일치하는 상품 정보가 없습니다"));
    }

    private Factories validateFactory(Long factory_id) {
        return factoryRepository.findById(factory_id).orElseThrow(() -> new IllegalArgumentException("일치하는 공장 정보가 없습니다"));
    }

}
