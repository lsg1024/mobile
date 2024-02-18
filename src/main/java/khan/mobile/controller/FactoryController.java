package khan.mobile.controller;

import jakarta.validation.Valid;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.dto.response.ErrorResponse;
import khan.mobile.dto.FactoryDto;
import khan.mobile.dto.response.SuccessResponse;
import khan.mobile.service.FactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FactoryController {

    private final FactoryService factoryService;

    @GetMapping("api/factory")
    public Page<FactoryDto> getFactoryList(Pageable pageable) {
        return factoryService.getFactoryList(pageable);
    }

    @PostMapping("api/factory")
    public ResponseEntity<CommonResponse> createFactory(@RequestHeader("userId") Long userId,
                                                        @Valid @RequestBody FactoryDto.Create createDto,
                                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(new CommonResponse("공장 생성 실패", errors));
        }

        factoryService.createFactory(userId, createDto);
        return ResponseEntity.ok(new CommonResponse("생성 완료"));
    }

    @PostMapping("/api/factory/update")
    public ResponseEntity<CommonResponse> updateFactory(@Valid @RequestBody FactoryDto.Update updateDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(new CommonResponse("공장 수정 실패", errors));
        }

        factoryService.updateFactories(updateDto);
        return ResponseEntity.ok(new CommonResponse("수정 완료"));
    }

    @GetMapping("/api/factory/search")
    public Page<FactoryDto> getSearchFactoryList(@RequestParam("factorySearch") String factoryName, Pageable pageable) {
        return factoryService.getSearchFactoryList(factoryName, pageable);
    }

}
