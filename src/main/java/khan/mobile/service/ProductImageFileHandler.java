package khan.mobile.service;

import khan.mobile.entity.ProductImage;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class ProductImageFileHandler {

    public List<ProductImage> parseFileInfo(Long productId, List<MultipartFile> images) throws IOException {

        List<ProductImage> imageList = new ArrayList<>();

        if (images.isEmpty()) {
            return imageList;
        }

        // 현재 날짜를 기반으로 디렉토리 이름 생성
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 디렉토리 내부의 상대 경로 설정
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "images/" + productId + "/" + current_date;
        File file = new File(absolutePath + path);

        if (!file.exists()) {
            file.mkdirs();
        }

        for (MultipartFile multipartFile : images) {
            if (!multipartFile.isEmpty()) {
                String contentType = multipartFile.getContentType();
                String originalFileExtension;

                if (ObjectUtils.isEmpty(contentType)) {
                    continue;
                }
                else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    } else {
                        continue; // 지원하지 않는 파일 타입이면 스킵
                    }
                }

                // UUID를 이용하여 고유한 파일 이름 생성
                String newFileName = UUID.randomUUID().toString() + "_" + current_date + "_" + multipartFile.getOriginalFilename();

                // 상품 이미지 정보 생성 및 리스트에 추가
                ProductImage productImage = ProductImage.builder()
                        .imageName(newFileName)
                        .imagePath(path + "/" + newFileName)
                        .imageOriginName(multipartFile.getOriginalFilename())
                        .build();

                imageList.add(productImage);

                file = new File(absolutePath + path + "/" + newFileName);
                multipartFile.transferTo(file);
            }
        }

        return imageList;

    }

}
