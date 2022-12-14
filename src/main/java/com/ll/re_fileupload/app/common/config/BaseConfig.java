package com.ll.re_fileupload.app.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class BaseConfig {
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    public static String GET_FILE_DIR_PATH;

    @Value("${custom.genFileDirPath}")
    public void setFileDirPath(String genFileDirPath) {
        GET_FILE_DIR_PATH = genFileDirPath;
    }
}
