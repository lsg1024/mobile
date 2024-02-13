package khan.mobile.controller;

import jakarta.servlet.http.HttpSession;
import khan.mobile.dto.ProductDto;
import khan.mobile.dto.ResponseDto;
import khan.mobile.dto.StoreDto;
import khan.mobile.service.ProductService;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class testController {

    private final ProductService productService;
    private final StoreService storeService;

    @GetMapping("/api/product")
    public Page<ProductDto> getAPIProductList(@PageableDefault(size = 9) Pageable pageable) {
        return productService.getProductList(pageable);
    }

    @GetMapping("/api/product/search")
    public Page<ProductDto> getProductSearchList(@RequestParam("productSearch") String productName, @PageableDefault(size = 9) Pageable pageable) {
        return productService.getSearchProductList(productName, pageable);
    }

    @GetMapping("/api/product/detail/{id}")
    public ProductDto getProductDetail(@PathVariable("id") Long id) {
        return productService.getProductDetail(id);
    }

    @PostMapping("/api/excel/read")
    public ResponseEntity<List<StoreDto>> readExcelToJson(@RequestParam("file") MultipartFile file) throws IOException {
        List<StoreDto> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (extension == null || (!extension.equals("xlsx") && !extension.equals("xls"))) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = extension.equals("xlsx") ? new XSSFWorkbook(file.getInputStream()) : new HSSFWorkbook(file.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            StoreDto data = new StoreDto();

            double numericValue = row.getCell(0).getNumericCellValue();
            long longValue = (long) numericValue;

            data.setStoreId(longValue);
            data.setStoreName(row.getCell(5).getStringCellValue());

            dataList.add(data);
        }

        return ResponseEntity.ok(dataList);
    }
    @GetMapping("/api/stores")
    public Page<StoreDto> StoreList(Pageable pageable) {
        return storeService.getStoreList(pageable);
    }

    @GetMapping("/api/stores/search")
    public Page<StoreDto> getSearchStoresList(@RequestParam("storeSearch") String storeName, Pageable pageable) {
        return storeService.getSearchResult(storeName, pageable);
    }

    @PostMapping("/api/stores/save")
    public ResponseEntity<ResponseDto> saveStores(@RequestBody List<StoreDto> dataList) {

        for (StoreDto data : dataList) {
            StoreDto stores = StoreDto.builder()
                    .storeName(data.getStoreName())
                    .build();
            storeService.saveStores(stores);
        }

        return ResponseEntity.ok(new ResponseDto("데이터 저장 성공"));

    }

    @PostMapping("/stores/update")
    public ResponseEntity<ResponseDto> StoreUpdate(@RequestParam("storeId") String storeId, @Validated @RequestBody StoreDto storeDto) {
        storeService.updateStores(storeId, storeDto);
        return ResponseEntity.ok(new ResponseDto("수정 완료"));
    }

}
