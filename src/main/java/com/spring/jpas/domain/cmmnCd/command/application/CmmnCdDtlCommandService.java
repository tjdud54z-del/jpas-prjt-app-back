package com.spring.jpas.domain.cmmnCd.command.application;

import com.spring.jpas.domain.cmmnCd.command.domain.CmmnCdDtl;
import com.spring.jpas.domain.cmmnCd.command.domain.CmmnCdDtlId;
import com.spring.jpas.domain.cmmnCd.command.dto.CmmnCdDtlDto;
import com.spring.jpas.domain.cmmnCd.command.infra.CmmnCdDtlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class CmmnCdDtlCommandService {

    private final CmmnCdDtlRepository repo;

    public void saveBatch(
        List<CmmnCdDtlDto> reqs
    ) {
        for (var r : reqs) {
            switch (r.getCrudType()) {
                case I, U -> saveOrUpdate(r);
                case D -> delete(r);
            }
        }
    }

    private void saveOrUpdate(CmmnCdDtlDto r) {
        CmmnCdDtlId id = new CmmnCdDtlId();
        id.setCommonCode(r.getCommonCode());
        id.setCommonCodeDtl(r.getCommonCodeDtl());

        CmmnCdDtl e = repo.findById(id).orElseGet(CmmnCdDtl::new);

        e.setCommonCode(r.getCommonCode());
        e.setCommonCodeDtl(r.getCommonCodeDtl());
        e.setCommonCodeDtlName(r.getCommonCodeDtlName());
        e.setActiveYn(r.getActiveYn());
        e.setSortOrder(r.getSortOrder());
        e.setDescription(r.getDescription());

        e.setAttr1(r.getAttr1());
        e.setAttr2(r.getAttr2());
        e.setAttr3(r.getAttr3());
        e.setAttr4(r.getAttr4());
        e.setAttr5(r.getAttr5());
        e.setAttr6(r.getAttr6());
        e.setAttr7(r.getAttr7());
        e.setAttr8(r.getAttr8());
        e.setAttr9(r.getAttr9());
        e.setAttr10(r.getAttr10());

        if (e.getCreatedUserId() == null) {
            e.setCreatedUserId(r.getCreatedUserId());
        }
        e.setUpdatedUserId(r.getUpdatedUserId());

        repo.save(e);
    }

    private void delete(CmmnCdDtlDto r) {
        CmmnCdDtlId id = new CmmnCdDtlId();
        id.setCommonCode(r.getCommonCode());
        id.setCommonCodeDtl(r.getCommonCodeDtl());
        repo.deleteById(id);
    }

}
