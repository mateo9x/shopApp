package com.mateo9x.shop.controller;

import com.mateo9x.shop.domain.SqlVersion;
import com.mateo9x.shop.repository.SqlVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppController {

    private final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    SqlVersionRepository sqlVersionRepository;

    @GetMapping("/sql-version")
    public SqlVersion getSqlVersion() {
        log.debug("REST request to get sql version");
        SqlVersion sqlVersion = sqlVersionRepository.findFirstBy();
        if (sqlVersion != null) {
            return sqlVersion;
        } else {
            return null;
        }
    }

}
