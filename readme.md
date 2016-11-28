
## canalx-select-db

Fetch db-data to JSON style 

## quick start 

    @Test
    public void test() {

        String driverClass = "com.mysql.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306?"
                + "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries"
                + "=true";
        String userName = "root";
        String password = "123456";

        try {

            DbFetcher dbFetcher = DbFetcherFactory.getDefaultDbFetcher(driverClass, dbUrl, userName, password);

            List<Map<String, Object>> results = dbFetcher.executeSql("select * from 100weidu.user");
            for (Map<String, Object> map : results) {
                System.out.println(map.toString());
            }

        } catch (Exception e) {

            e.printStackTrace();

            Assert.assertTrue(false);
        }
    }
    
## 推荐

- 有态度无广告的搜索引擎: https://www.sov5.com
- 高质量的微信公众号阅读: http://www.100weidu.com
- Python中国社区: http://www.python88.com
