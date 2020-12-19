package com.loyayz.simple.db.mybatis.extension;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;

import java.util.List;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MybatisUtils {

    public static <R> List<R> executeSelectList(Class<?> clazz, String sqlMethod, Object param) {
        String statement = MybatisUtils.sqlStatement(clazz, sqlMethod);
        SqlSession sqlSession = SqlHelper.sqlSession(clazz);
        try {
            return sqlSession.selectList(statement, param);
        } finally {
            closeSqlSession(clazz, sqlSession);
        }
    }

    public static <R> R executeSelectOne(Class<?> clazz, String sqlMethod, Object param) {
        String statement = MybatisUtils.sqlStatement(clazz, sqlMethod);
        SqlSession sqlSession = SqlHelper.sqlSession(clazz);
        try {
            return sqlSession.selectOne(statement, param);
        } finally {
            closeSqlSession(clazz, sqlSession);
        }
    }

    public static boolean executeInsert(Class<?> clazz, String sqlMethod, Object param) {
        String statement = MybatisUtils.sqlStatement(clazz, sqlMethod);
        SqlSession sqlSession = SqlHelper.sqlSession(clazz);
        try {
            return SqlHelper.retBool(sqlSession.insert(statement, param));
        } finally {
            closeSqlSession(clazz, sqlSession);
        }
    }

    public static boolean executeUpdate(Class<?> clazz, String sqlMethod, Object param) {
        String statement = MybatisUtils.sqlStatement(clazz, sqlMethod);
        SqlSession sqlSession = SqlHelper.sqlSession(clazz);
        try {
            return SqlHelper.retBool(sqlSession.update(statement, param));
        } finally {
            closeSqlSession(clazz, sqlSession);
        }
    }

    public static boolean executeDelete(Class<?> clazz, String sqlMethod, Object param) {
        String statement = MybatisUtils.sqlStatement(clazz, sqlMethod);
        SqlSession sqlSession = SqlHelper.sqlSession(clazz);
        try {
            return SqlHelper.retBool(sqlSession.delete(statement, param));
        } finally {
            closeSqlSession(clazz, sqlSession);
        }
    }

    public static GlobalConfig getGlobalConfig(Class<?> clazz) {
        Configuration configuration = TableInfoHelper.getTableInfo(clazz).getConfiguration();
        return GlobalConfigUtils.getGlobalConfig(configuration);
    }

    public static IdentifierGenerator getIdentifierGenerator(Class<?> clazz) {
        return getGlobalConfig(clazz).getIdentifierGenerator();
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    public static void closeSqlSession(Class<?> clazz, SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(clazz));
    }

    /**
     * 获取SqlStatement
     */
    public static String sqlStatement(Class<?> clazz, String sqlMethod) {
        return SqlHelper.table(clazz).getSqlStatement(sqlMethod);
    }

}
