package com.fastcampus.demographql.domain.entity;

import java.util.Date;

public record Version(String name, Date releaseDate) {
    public static Version getVersion() {
        return new Version("1.0.0", new Date());
    }
}
