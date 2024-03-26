package khan.mobile.controller.excel;

import khan.mobile.dto.CommonDto;
import khan.mobile.dto.StoreDto;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
    @PostMapping("/excel/read")
    public ResponseEntity<List<CommonDto.StoreAndFactory>> readExcelToJson(@RequestParam("file") MultipartFile file) throws IOException {
        List<CommonDto.StoreAndFactory> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (extension == null || (!extension.equals("xlsx") && !extension.equals("xls"))) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = extension.equals("xlsx") ? new XSSFWorkbook(file.getInputStream()) : new HSSFWorkbook(file.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            CommonDto.StoreAndFactory data = new CommonDto.StoreAndFactory();

            double numericValue = row.getCell(0).getNumericCellValue();
            long longValue = (long) numericValue;

            data.setId(longValue);
            // 혹시 모를 문자열만 있는 이름을 문자열로 전환
            data.setName(dataFormatter.formatCellValue(row.getCell(5)));

            dataList.add(data);
        }

        return ResponseEntity.ok(dataList);
    }
}
