package khan.mobile.controller;

import khan.mobile.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductImageController {

    private final ProductImageService productImageService;

    @GetMapping("/images/")
    public ResponseEntity<Resource> getImageAsResource(@RequestParam("imagePath") String imagePath) {

        log.info("getImageAsResource");

        try {
            Path filePath = Paths.get(imagePath);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/images/")
    public ResponseEntity<?> deleteImage(@RequestParam("imageId") Long imageId) {

        log.info("deleteImage");

        try {
            productImageService.deleteImage(imageId);
            return ResponseEntity.ok().body("이미지가 성공적으로 삭제되었습니다.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 삭제 중 오류가 발생했습니다.");
        }
    }

}
