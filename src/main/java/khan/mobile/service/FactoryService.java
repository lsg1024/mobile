package khan.mobile.service;

import khan.mobile.dto.FactoryDto;
import khan.mobile.entity.Factories;
import khan.mobile.repository.FactoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FactoryService {

    private final FactoryRepository factoryRepository;

    //공장 생성
    @Transactional
    public void createFactory(FactoryDto.Create createDto) {

        Factories factory = Factories.builder()
                .factoryName(createDto.getFactoryName())
                .build();

        factoryRepository.save(factory);
    }

    //공장 리스트
    public Page<FactoryDto> getFactoryList(Pageable pageable) {
        return factoryRepository.findAll(pageable)
                .map(FactoryDto::factoryDto);
    }

    //공장 저장
    @Transactional
    public void saveFactories(FactoryDto.Create createDto) {

        Factories factories = Factories.builder()
                .factoryName(createDto.getFactoryName())
                .build();

        factoryRepository.save(factories);
    }

    //공장 수정
    @Transactional
    public void updateFactories(FactoryDto.Update updateDto) {

        Factories findFactory = getFactories(updateDto.getFactoryId());

        findFactory.updateFactoryName(updateDto);

        factoryRepository.save(findFactory);

    }

    //공장 삭제
    @Transactional
    public void deleteFactory(FactoryDto.Delete dto) {
        Factories findFactory = getFactories(dto.getFactoryId());

        factoryRepository.delete(findFactory);
    }

    //공장 검색
    public Page<FactoryDto> getSearchFactoryList(String factoryName, Pageable pageable) {
        Page<Factories> result = factoryRepository.findByFactoryNameContaining(factoryName, pageable);

        return result.map(FactoryDto::factoryDto);
    }

    public Factories validateFactory(String factoryName) {
        return factoryRepository.findByFactoryName(factoryName).orElseThrow(() -> new IllegalArgumentException("일치하는 공장 정보가 없습니다"));
    }

    private Factories getFactories(Long factoryId) {
        return factoryRepository.findById(factoryId).orElseThrow(() ->  new IllegalStateException("존재하지 않은 공장 정보 입니다."));
    }

}
