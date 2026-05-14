package com.spring.jpas.domain.cmmnCd.command.application;

import com.spring.jpas.domain.cmmnCd.command.domain.CmmnCd;
import com.spring.jpas.domain.cmmnCd.command.dto.CmmnCdDto;
import com.spring.jpas.domain.cmmnCd.command.infra.CmmnCdDtlRepository;
import com.spring.jpas.domain.cmmnCd.command.infra.CmmnCdRepository;
import com.spring.jpas.global.common.CrudType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class CmmnCdCommandService {


    private final CmmnCdRepository repository;
    private final CmmnCdDtlRepository subRepository;

    public void saveBatch(List<CmmnCdDto> reqList) {

//        // 공통코드 CUD 처리
//        for (CmmnCdDto req : reqList) {
//            switch (req.getCrudType()) {
//                case I -> insert(req);
//                case U -> update(req);
//                case D -> delete(req);
//            }
//        }

        // 삭제 먼저 처리 (하위 → 상위 순)
        reqList.stream()
            .filter(r -> r.getCrudType() == CrudType.D)
            .forEach(r -> deleteCommon(r.getCommonCode()));

        // 그 외 I/U
        reqList.stream()
            .filter(r -> r.getCrudType() != CrudType.D)
            .forEach(r -> upsertCommon(r));


    }

    private void deleteCommon(String commonCode) {
        // 서브코드 전체 삭제
        subRepository.deleteByCommonCode(commonCode);
        // 공통코드 삭제
        repository.deleteById(commonCode);
    }

    private void upsertCommon(CmmnCdDto req) {

        CmmnCd e = repository.findById(req.getCommonCode()).orElseGet(CmmnCd::new);

        e.setCommonCode(req.getCommonCode());
        e.setCommonCodeName(req.getCommonCodeName());
        e.setActiveYn(req.getActiveYn());
        e.setSortOrder(req.getSortOrder());
        e.setDescription(req.getDescription());

        e.setAttr1(req.getAttr1());
        e.setAttr2(req.getAttr2());
        e.setAttr3(req.getAttr3());
        e.setAttr4(req.getAttr4());
        e.setAttr5(req.getAttr5());
        e.setAttr6(req.getAttr6());
        e.setAttr7(req.getAttr7());
        e.setAttr8(req.getAttr8());
        e.setAttr9(req.getAttr9());
        e.setAttr10(req.getAttr10());

        if (e.getCreatedUserId() == null) {
            e.setCreatedUserId(req.getCreatedUserId());
        }
        e.setUpdatedUserId(req.getUpdatedUserId());

        repository.save(e);
    }


    private void insert(CmmnCdDto req) {
        // 이미 존재하면 에러 처리
        if (repository.existsById(req.getCommonCode())) {
            throw new IllegalStateException("이미 존재하는 공통코드입니다: " + req.getCommonCode());
        }

        CmmnCd e = new CmmnCd();
        e.setCommonCode(req.getCommonCode());
        e.setCommonCodeName(nvl(req.getCommonCodeName(), "")); // NOT NULL이면 방어
        e.setActiveYn(nvl(req.getActiveYn(), "Y"));
        e.setSortOrder(req.getSortOrder() == null ? 0 : req.getSortOrder());
        e.setDescription(req.getDescription());

        e.setAttr1(req.getAttr1());
        e.setAttr2(req.getAttr2());
        e.setAttr3(req.getAttr3());
        e.setAttr4(req.getAttr4());
        e.setAttr5(req.getAttr5());
        e.setAttr6(req.getAttr6());
        e.setAttr7(req.getAttr7());
        e.setAttr8(req.getAttr8());
        e.setAttr9(req.getAttr9());
        e.setAttr10(req.getAttr10());

        // 필수 감사 컬럼
        e.setCreatedUserId(req.getCreatedUserId());
        e.setUpdatedUserId(req.getUpdatedUserId());

        repository.save(e); // 새 엔티티 저장
    }

    private void update(CmmnCdDto req) {
        CmmnCd e = repository.findById(req.getCommonCode())
                .orElseThrow(() -> new IllegalStateException("수정 대상 공통코드가 없습니다: " + req.getCommonCode()));

        // 필요한 값만 갱신
        if (req.getCommonCodeName() != null) e.setCommonCodeName(req.getCommonCodeName());
        if (req.getActiveYn() != null) e.setActiveYn(req.getActiveYn());
        if (req.getSortOrder() != null) e.setSortOrder(req.getSortOrder());
        if (req.getDescription() != null) e.setDescription(req.getDescription());

        e.setAttr1(req.getAttr1());
        e.setAttr2(req.getAttr2());
        e.setAttr3(req.getAttr3());
        e.setAttr4(req.getAttr4());
        e.setAttr5(req.getAttr5());
        e.setAttr6(req.getAttr6());
        e.setAttr7(req.getAttr7());
        e.setAttr8(req.getAttr8());
        e.setAttr9(req.getAttr9());
        e.setAttr10(req.getAttr10());

        // 수정자 필수
        e.setUpdatedUserId(req.getUpdatedUserId());

        // save 호출 없이도 트랜잭션 종료 시 더티체킹으로 update 될 수 있음(일반적인 JPA 패턴)
        // 명시적으로 하고 싶으면 repository.save(e)도 가능 (기존이면 merge)
    }

    private void delete(CmmnCdDto req) {
        repository.deleteById(req.getCommonCode());
    }

    private String nvl(String v, String def) {
        return (v == null || v.isBlank()) ? def : v;
    }

}
