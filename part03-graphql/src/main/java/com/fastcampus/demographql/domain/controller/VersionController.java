package com.fastcampus.demographql.domain.controller;

import com.fastcampus.demographql.domain.entity.Version;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class VersionController {
    @QueryMapping
    public Version getVersion() {
        return Version.getVersion();
    }
}
