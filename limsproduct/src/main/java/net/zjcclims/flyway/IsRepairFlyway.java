package net.zjcclims.flyway;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class IsRepairFlyway {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DataSource dataSource;

    private boolean baselineOnMigrate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setBaselineOnMigrate(boolean baselineOnMigrate) {
        this.baselineOnMigrate = baselineOnMigrate;
    }

    public void migrate() {
        //初始化flyway类
        Flyway flyway = new Flyway();
        //设置加载数据库的相关配置信息
        flyway.setDataSource(dataSource);
        //设置存放flyway metadata数据的表名，默认"schema_version"，可不写
        flyway.setTable("flyway_schema_history");
        //设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径，默认"db/migration"，可不写
        //设置sql脚本文件的编码，默认"UTF-8"，可不写
        flyway.setEncoding("UTF-8");
        flyway.setBaselineOnMigrate(this.baselineOnMigrate);

        try {
            flyway.migrate();
        } catch (final Exception e) {
            logger.error("Flyway migration failed, doing a repair and retrying ...");
            flyway.repair();
            flyway.migrate();
        }
    }
}