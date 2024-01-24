package khan.mobile.controller.excel;

import jakarta.servlet.http.HttpSession;
import khan.mobile.dto.StoreDto;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreExcelController {

    private final StoreService storeService;

    @GetMapping("/excel")
    public String upload() {
        return "productPages/excel";
    }

    @PostMapping("/excel/read")
    public String readExcel(@RequestParam("file") MultipartFile file, HttpSession session,  Model model)
            throws IOException {

        List<StoreDto> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        assert extension != null;
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

            Row row = worksheet.getRow(i);

            StoreDto data = new StoreDto();

            double numericValue = row.getCell(0).getNumericCellValue();
            long longValue = (long) numericValue;

            data.setStore_id(longValue);
            data.setStore_name(row.getCell(5).getStringCellValue());

            dataList.add(data);
        }

        session.setAttribute("excelData", dataList);

        model.addAttribute("datas", dataList);

        return "storePages/excelList";

    }

    @PostMapping("/stores/save")
    public String saveStores(HttpSession session, RedirectAttributes redirectAttributes) {

        List<StoreDto> dataList = (List<StoreDto>) session.getAttribute("excelData");

        for (StoreDto data : dataList) {
            StoreDto stores = StoreDto.builder()
                    .store_name(data.getStore_name())
                    .build();
            storeService.saveStores(stores);
        }

        session.removeAttribute("excelData");

        redirectAttributes.addFlashAttribute("successMessage", "데이터 저장 성공");

        return "redirect:storePages/excel";

    }
}
