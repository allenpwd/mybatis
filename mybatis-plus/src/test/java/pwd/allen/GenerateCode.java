package pwd.allen;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 代码生成器
 *
 * @author 门那粒沙
 * @create 2020-06-20 16:34
 **/
public class GenerateCode {

    /**
     * 根据db_user、db_department两张表生成代码到桌面/generate-by-mp/
     * @throws IOException
     */
    @Test
    public void one() throws IOException {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //<editor-fold desc="配置GlobalConfig">
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.home") + "/Desktop/generate-by-mp")
                .setAuthor("pwd")
                .setActiveRecord(false) //实体类是否继承Model来支持AR
                // 是否覆盖已有文件
                .setFileOverride(true)
                // 是否打开输出目录
                .setOpen(false)
                .setIdType(IdType.AUTO)
                .setBaseColumnList(true) //是否在xml中产生BaseColumnList
                .setBaseResultMap(true)
                .setServiceName("I%sService");
        mpg.setGlobalConfig(globalConfig);
        //</editor-fold>

        //<editor-fold desc="配置 DataSourceConfig">
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties"));
        dataSourceConfig.setUrl(properties.getProperty("jdbc.url"));
        dataSourceConfig.setDriverName(properties.getProperty("jdbc.driver"));
        dataSourceConfig.setUsername(properties.getProperty("jdbc.username"));
        dataSourceConfig.setPassword(properties.getProperty("jdbc.password"));
        mpg.setDataSource(dataSourceConfig);
        //</editor-fold>

        //<editor-fold desc="包配置">
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("generate")
                .setParent("pwd.allen")
                .setEntity("entity")
                .setXml("mapper.xml");
        mpg.setPackageInfo(pc);
        //</editor-fold>

        //<editor-fold desc="自定义代码模板">
        //指定自定义模板路径, 位置：/resources/templates/entity.java.ftl(或者是.vm)
        //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("/templates/entity.java")
                .setController("/templates/controller.java")
                .setService("/templates/service.java")
                .setServiceImpl("/templates/serviceImpl.java")
                .setMapper("/templates/mapper.java")
                .setXml("/templates/mapper.xml");
        //配置自定义模板
        mpg.setTemplate(templateConfig);
        //</editor-fold>


        //<editor-fold desc="自定义属性注入">
        InjectionConfig injectionConfig = new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        //配置自定义属性注入
        mpg.setCfg(injectionConfig);
        //</editor-fold>


        //<editor-fold desc="策略配置 可指定需要生成哪些表或者排除哪些表">
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true) //全局大写命名
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel) // 默认按naming
//              .setSuperEntityClass("pwd.allen.entity.BasicEntity")// 你自己的父类实体,没有就不用设置!
//              .setSuperEntityColumns("id")// 写于父类中的公共字段
                .setEntityLombokModel(true)
                .setTablePrefix(pc.getModuleName() + "_")  //TODO 这个有什么作用吗
                .setRestControllerStyle(true)
//              .setSuperControllerClass("你自己的父类控制器,没有就不用设置!") // 公共父类
                .setInclude("db_user", "db_department")  //表名
                .setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        //</editor-fold>

        // 设置模版引擎，默认VelocityTemplateEngine
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }
}