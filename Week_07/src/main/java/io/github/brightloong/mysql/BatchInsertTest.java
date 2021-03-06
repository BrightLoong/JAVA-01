package io.github.brightloong.mysql;

import io.github.brightloong.mysql.utils.JDBCUtils;
import io.reactivex.Observable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author BrightLoong
 * @date 2021/3/3 16:32
 * @description
 */
public class BatchInsertTest {

    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    static {
        //预热
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeJDBCResourceQuiet(connection, null, null);
        }
    }

    public static void main(String[] args) {
        insertWithAddBatch();
    }


    private static void insertWithAddBatch() {
        String sql = "insert into `order`(order_no, user_id, order_time, order_price, user_address_id, order_status,order_pay ) VALUES ( ?, 1, now(), 1.00, 1, 1, 1.00 )";
        long start = System.currentTimeMillis();
        List<Future<?>> futures = new ArrayList<>();
        Observable.range(1, 1000000).buffer(10000).subscribe(v -> {
            Future<?> submit = executor.submit(() -> {
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                try {
                    connection = JDBCUtils.getConnection(true);
                    preparedStatement = connection.prepareStatement(sql);
                    for (int index : v) {
                        preparedStatement.setString(1, "" + index);
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.closeJDBCResourceQuiet(connection, preparedStatement, null);
                }
            });
            futures.add(submit);
        });
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行耗时：" + (System.currentTimeMillis() - start));
    }

    private static void insertValues() {
        StringBuilder stringBuilder = new StringBuilder("insert into `order`(order_no, user_id, order_time, order_price, user_address_id, order_status,order_pay ) VALUES");
        for (int i = 1; i <= 10000; i++) {
            stringBuilder.append("( ?, 1, '2021-02-01 00:00:00', 1.00, 1, 1, 1.00 )");
            stringBuilder.append(i == 10000 ? ";" : ",");
        }
        String sql = stringBuilder.toString();
        long start = System.currentTimeMillis();
        List<Future<?>> futures = new ArrayList<>();
        Observable.range(1, 1000000).buffer(100000).subscribe(v -> {
            Future<?> submit = executor.submit(() -> {
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                try {
                    connection = JDBCUtils.getConnection(true);
                    preparedStatement = connection.prepareStatement(sql);
                    int i = 1;
                    for (int index : v) {
                        preparedStatement.setString(i++, "" + index);
                    }
                    preparedStatement.executeUpdate();
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.closeJDBCResourceQuiet(connection, preparedStatement, null);
                }
            });
            futures.add(submit);
        });
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行耗时：" + (System.currentTimeMillis() - start));
    }
}
