package com.github.knightliao.canalx.db.config;

import lombok.Data;

@Data
public class DbConfig {

    String driverClass;

    String dbName;

    String dbUrl;

    String userName;

    String password;

}
