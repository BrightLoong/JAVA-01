package io.github.brightloong.spring.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BrightLoong
 * @date 2021/2/8 16:09
 * @description
 */
public class JDBCDemo {
    public static void main(String[] args) {
        JDBCDemo jdbcDemo = new JDBCDemo();
        jdbcDemo.insertCity("成都");
        jdbcDemo.insertCity("自贡");
        jdbcDemo.insertCity("绵阳");

        jdbcDemo.deleteCityById(1L);

        jdbcDemo.updateCityNameById(2L, "德阳");

        jdbcDemo.batchInsert();

        System.out.println(jdbcDemo.selectCityAll());
    }


    public void batchInsert() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            connection = JDBCUtils.getConnection(false);
            //不主动提交
            connection.setAutoCommit(false);
            // 定义sql语句?表示占位符
            String sql = "insert into city(name) values (?)";
            // 获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1, "遂宁");
            preparedStatement.addBatch();

            preparedStatement.setString(1, "达州");
            preparedStatement.addBatch();


            preparedStatement.executeBatch();

            //提交事务
            connection.commit();
        } catch (Exception e) {
            JDBCUtils.rollback(connection);
            e.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtils.closeJDBCResourceQuiet(connection, preparedStatement, null);
        }
    }


    public int insertCity(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            connection = JDBCUtils.getConnection(false);
            // 定义sql语句?表示占位符
            String sql = "insert into city(name) values (?)";
            // 获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1, name);
            // 向数据库发出sql
            i = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtils.closeJDBCResourceQuiet(connection, preparedStatement, null);
        }
        return i;
    }

    public int deleteCityById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            connection = JDBCUtils.getConnection(true);
            // 定义sql语句?表示占位符
            String sql = "delete from city where id = ?";
            // 获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setLong(1, id);
            // 向数据库发出sql
            i = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtils.closeJDBCResourceQuiet(connection, preparedStatement, null);
        }
        return i;
    }

    public int updateCityNameById(Long id, String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            connection = JDBCUtils.getConnection(true);
            // 定义sql语句?表示占位符
            String sql = "update city set name = ? where id = ?";
            // 获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            // 向数据库发出sql
            i = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtils.closeJDBCResourceQuiet(connection, preparedStatement, null);
        }
        return i;
    }

    public List<City> selectCityAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<City> result = new ArrayList<>();
        int i;
        try {
            connection = JDBCUtils.getConnection(false);
            // 定义sql语句?表示占位符
            String sql = "select * from  city";
            // 获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            // 向数据库发出sql
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getLong("id"));
                city.setName(resultSet.getString("name"));
                result.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            JDBCUtils.closeJDBCResourceQuiet(connection, preparedStatement, null);
        }
        return result;
    }

}
