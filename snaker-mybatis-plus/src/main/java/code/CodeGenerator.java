package code;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 *
 * 〈代码生成工具类〉<br>
 *
 * @author jwy
 * @fileName: CodeGenerator
 * @date: 23/05/2019 15:08
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CodeGenerator {
    private static final String PATH = "/Users/zhangsen/IdeaProjects/snakerflow/snaker-core/src/main/java";
    private static final String TABLENAME = "wf_task_actor";
    private static final String CODE_MODEL_PATH = "org.snaker.engine";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/flow?characterEncoding=utf-8&useSSL=false";


    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //调整输出文件的路径
        gc.setOutputDir(PATH);
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        // 作者
        gc.setAuthor("jason");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
/*        gc.setControllerName("%sController");*/
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(USER_NAME);
        dsc.setPassword(PASSWORD);
        dsc.setUrl(DB_URL);
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //添加了lombok注解，@Data直接注解在类上，去掉了getter和setter方法
        strategy.setEntityLombokModel(true);
        //生成@RestController
        strategy.setRestControllerStyle(true);
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //驼峰形式
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(TABLENAME);

        strategy.setTablePrefix("wf_");

        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(CODE_MODEL_PATH);
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service");
        pc.setMapper("mapper");
        pc.setEntity("entity.po");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
        // 执行生成
        mpg.execute();
    }
}
