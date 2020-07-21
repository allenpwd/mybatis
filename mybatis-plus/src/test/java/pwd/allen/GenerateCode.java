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
                .setAuthor("pwdan")
                .setActiveRecord(false) //实体类是否继承Model来支持AR
                // 是否覆盖已有文件
                .setFileOverride(true)
                // 是否打开输出目录
                .setOpen(false)
                .setIdType(IdType.AUTO)
                .setBaseColumnList(true) //是否在xml中产生BaseColumnList
                .setBaseResultMap(true)
                .setMapperName("I%sDao")
                .setServiceName("I%sService");
        mpg.setGlobalConfig(globalConfig);
        //</editor-fold>

        //<editor-fold desc="配置 DataSourceConfig">
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties"));
        dataSourceConfig.setUrl(properties.getProperty("oracle.url"));
        dataSourceConfig.setDriverName(properties.getProperty("oracle.driver"));
        dataSourceConfig.setUsername(properties.getProperty("oracle.username"));
        dataSourceConfig.setPassword(properties.getProperty("oracle.password"));
        mpg.setDataSource(dataSourceConfig);
        //</editor-fold>

        //<editor-fold desc="包配置">
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("generate")
                .setParent("com.cdc")
                .setEntity("entity")
                .setXml("mapper.xml");
        mpg.setPackageInfo(pc);
        //</editor-fold>

        //<editor-fold desc="自定义代码模板">
        //指定自定义模板路径, 位置：/resources/templates/entity.java.ftl(或者是.vm)
        //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("/template/entity.java")
                .setController("/template/controller.java")
                .setService("/template/service.java")
                .setServiceImpl("/template/serviceImpl.java")
                .setMapper("/template/mapper.java")
                .setXml("/template/mapper.xml");
        //配置自定义模板
        mpg.setTemplate(templateConfig);
        //</editor-fold>


        //<editor-fold desc="自定义属性注入">
        InjectionConfig injectionConfig = new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
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
                .setSuperMapperClass("com.cdc.common.dao.IBaseDAO")
                .setSuperServiceClass("com.cdc.common.service.IBaseService")
                .setSuperServiceImplClass("com.cdc.common.service.impl.AbstractService")
                .setSuperControllerClass("com.cdc.api.apiCommon.baseController.BasicController")
                .setRestControllerStyle(true)
                .setEntityLombokModel(false)
                .setTablePrefix("PUB_")  //TODO 这个有什么作用吗
                .setRestControllerStyle(true)
//              .setSuperControllerClass("你自己的父类控制器,没有就不用设置!") // 公共父类
                .setInclude("PUB_SUPERVISION", "PUB_SUPERVISION_GROUP", "PUB_SUPERVISION_GROUP_MEMBER", "PUB_SUPERVISION_DETAIL")  //表名
                .setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        //</editor-fold>

        // 设置模版引擎，默认VelocityTemplateEngine
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }
}
