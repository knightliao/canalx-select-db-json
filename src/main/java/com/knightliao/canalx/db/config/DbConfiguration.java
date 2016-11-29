package com.knightliao.canalx.db.config;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data
public class DbConfiguration {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigParser.class);

    Map<String, TableConfig> allTableInfo = new HashMap<String, TableConfig>(100);

    public static void parse(URL xmlPath) throws Exception {
        parse(xmlPath.openStream());
    }

    public static void parse(InputStream inputStream) throws Exception {

        DbConfiguration genConfiguration = new ConfigParser().parse(inputStream);

        LOGGER.debug(genConfiguration.getAllTableInfo().toString());
    }

}
