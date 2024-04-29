package khan.mobile.service;

import khan.mobile.dto.ProductDto;
import khan.mobile.entity.Factories;
import khan.mobile.entity.ProductImage;
import khan.mobile.entity.Products;
import khan.mobile.entity.Users;
import khan.mobile.repository.FactoryRepository;
import khan.mobile.repository.ProductImageRepository;
import khan.mobile.repository.ProductRepository;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final FactoryRepository factoryRepository;
    private final UserRepository userRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductImageFileHandler productImageFileHandler;

    //상품 생성
    @Transactional
    public void createProduct(Long userId, ProductDto.Create productDto, List<MultipartFile> images) throws IOException {

        Factories findFactoryId = validateFactory(productDto.getFactoryId());
        Users findUser = validateUser(userId);

        log.info("SerialNumber = {}",productDto.getSerialNumber());

        Products product = Products.builder()
                .productName(productDto.getName())
                .productSerialNumber(productDto.getSerialNumber())
                .productColor(productDto.getColor())
                .productWeight(productDto.getWeight())
                .productSize(productDto.getSize())
                .productOther(productDto.getOther())
                .productImage(new ArrayList<>())
                .user(findUser)
                .factory(findFactoryId)
                .build();

        Products savedProduct = productRepository.save(product);

        log.info("saveProduct Id = {}", savedProduct.getProductId());

        log.info("유효성 검사 완료");
        // 이미지 파일 처리
        if (images != null && !images.isEmpty()) {
            List<ProductImage> productImages = productImageFileHandler.parseFileInfo(savedProduct.getProductId(), images);
            for (ProductImage productImage : productImages) {
                productImage.setProduct(savedProduct); // 이미지 엔티티에 상품 연결
                productImageRepository.save(productImage); // 상품 이미지 정보 저장
            }
        } else {
            log.info("이미지 없음");
        }
    }

    //상품 수정
    @Transactional
    public void updateProduct(Long userId, Long productId, ProductDto.Update productDto, List<MultipartFile> images) throws IOException {

        Products findProduct = validateProduct(productId);
        Factories findFactory = validateFactory(productDto.getFactoryId());
        Users findUser = validateUser(userId);

        if (findProduct == null) {
            throw new IllegalArgumentException("일치하는 상품 정보가 없음");
        }

        // 상품 정보 수정
        findProduct.updateProduct(productDto);
        findProduct.setFactory(findFactory);
        findProduct.setUser(findUser);

        log.info("유효성 검사 완료");
        // 이미지 파일 처리
        if (images != null && !images.isEmpty()) {
            List<ProductImage> productImages = productImageFileHandler.parseFileInfo(productId, images);
            for (ProductImage productImage : productImages) {
                productImage.setProduct(findProduct); // ProductImage 엔티티에 상품 연결
                productImageRepository.save(productImage); // 상품 이미지 정보 저장
                log.info("updateProduct 이미지 저장 성공");
            }
        } else {
            log.info("이미지 없음");
        }

        productRepository.save(findProduct);
        log.info("updateProduct 저장 성공");

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

    private Factories validateFactory(Long factoryId) {
        return factoryRepository.findById(factoryId).orElseThrow(() -> new IllegalArgumentException("일치하는 공장 정보가 없습니다"));
    }

    private Users validateUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("일치하는 유저 정보가 없습니다"));
    }

}
