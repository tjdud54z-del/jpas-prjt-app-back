package com.spring.jpas.domain.cmmnCd.api;

import com.spring.jpas.domain.cmmnCd.command.application.CmmnCdCommandService;
import com.spring.jpas.domain.cmmnCd.command.application.CmmnCdDtlCommandService;
import com.spring.jpas.domain.cmmnCd.command.domain.CmmnCdDtl;
import com.spring.jpas.domain.cmmnCd.command.dto.CmmnCdDtlDto;
import com.spring.jpas.domain.cmmnCd.command.dto.CmmnCdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jpa")
@RequiredArgsConstructor
public class CmmnCdCommandController {

    private final CmmnCdCommandService service;
    private final CmmnCdDtlCommandService subService;

    @PostMapping("/cmmnCd/batch")
    public ResponseEntity<Void> saveCmmnCdBatch(@RequestBody List<CmmnCdDto> reqList) {
        service.saveBatch(reqList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cmmnCdDtl/batch")
    public ResponseEntity<Void> saveCmmnCdDtlBatch(@RequestBody List<CmmnCdDtlDto> reqList) {
        subService.saveBatch(reqList);
        return ResponseEntity.ok().build();
    }


}
