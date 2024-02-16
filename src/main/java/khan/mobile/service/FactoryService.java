package khan.mobile.service;

import khan.mobile.dto.FactoryDto;
import khan.mobile.entity.Factories;
import khan.mobile.repository.FactoryRepository;
import khan.mobile.repository.UserRepository;
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
    private final UserRepository userRepository;

    //공장 생성
    @Transactional
    public void createFactory(Long userId, FactoryDto factoryDto) {

        validateUser(userId);

        Factories factory = Factories.builder()
                .factoryName(factoryDto.getFactoryName())
                .build();

        factoryRepository.save(factory);
    }

    //공장 리스트
    public Page<FactoryDto> getFactoryList(Pageable pageable) {
        return factoryRepository.findAll(pageable)
                .map(FactoryDto::factoryDto);
    }

    //공장 수정
    @Transactional
    public void updateFactories(String factoryId, FactoryDto factoryDto) {


//        validateUser(userId);

        log.info("FactoryDto = {}, {}", factoryDto.getFactoryId(), factoryDto.getFactoryName());
        Factories findFactory = factoryRepository.findById(Long.parseLong(factoryId)).orElseThrow(() ->  new IllegalStateException("존재하지 않은 공장 정보 입니다."));

        log.info("FindFactory = {}, {}", findFactory.getFactoryId(), findFactory.getFactoryName());
        findFactory.updateFactoryName(factoryDto);

        factoryRepository.save(findFactory);

    }

    //공장 삭제

    //공장 검색
    public Page<FactoryDto> getSearchFactoryList(String factoryName, Pageable pageable) {
        Page<Factories> result = factoryRepository.findByFactoryNameContaining(factoryName, pageable);

        return result.map(FactoryDto::factoryDto);
    }

    private void validateUser(Long user_id) {
        userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("일치하는 유저 정보가 없습니다"));
    }
}
