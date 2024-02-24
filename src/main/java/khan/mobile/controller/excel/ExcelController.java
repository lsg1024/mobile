package khan.mobile.controller.excel;

import khan.mobile.dto.StoreDto;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExcelController {

    //엑셀 값 읽기
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
}
