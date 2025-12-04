package com.hutuya.project.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;
/**
 * <p>
 * 代码生成器
 * </p>
 *
 * @author hutuya
 * @since 2025-12-01
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:3306/backend_management?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true",
                        "hutuya",
                        "hu123123")

                .globalConfig(builder -> {
                    builder.author("hutuya")
                            .outputDir(projectPath + "/src/main/java")
                            .disableOpenDir();
                })

                .packageConfig(builder -> {
                    builder.parent("com.hutuya.project")
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    projectPath + "/src/main/resources/mapper"));
                })

                .strategyConfig(builder -> {
                    builder.addInclude("users", "user_roles","roles","role_permissions","permissions","operation_logs","login_logs")

                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .idType(IdType.AUTO)
                            .logicDeleteColumnName("is_deleted")
                            // 关键配置：添加自动填充注解
                            .addTableFills(new Column("created_at", FieldFill.INSERT))
                            .addTableFills(new Column("updated_at", FieldFill.INSERT_UPDATE))
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .fileOverride()

                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .fileOverride()

                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 接口名：%s代表表名首字母大写，如"Users" -> "UsersService"
                            .formatServiceImplFileName("%sServiceImpl") // 实现类名：如"UsersServiceImpl"
                            .fileOverride()

                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .fileOverride();
                })

                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}