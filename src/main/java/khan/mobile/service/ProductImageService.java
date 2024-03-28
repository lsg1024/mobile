package khan.mobile.service;

import khan.mobile.entity.ProductImage;
import khan.mobile.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public void deleteImage(Long imageId) throws IOException {

        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new FileNotFoundException("존재하지 않는 이미지 파일 입니다"));

        Path imagePath = Paths.get(image.getImagePath());

        Files.deleteIfExists(imagePath);

        productImageRepository.deleteById(imageId);

    }
}
