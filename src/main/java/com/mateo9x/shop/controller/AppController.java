package com.mateo9x.shop.controller;

import com.mateo9x.shop.domain.SqlVersion;
import com.mateo9x.shop.repository.SqlVersionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class AppController {

    private final SqlVersionRepository sqlVersionRepository;

    @GetMapping("/sql-version")
    public SqlVersion getSqlVersion() {
        log.debug("REST request to get sql version");
        return sqlVersionRepository.findFirstBy();
    }

}
