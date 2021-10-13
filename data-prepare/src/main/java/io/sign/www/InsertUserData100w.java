package io.sign.www;

import io.sign.www.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class InsertUserData100w {

    private static final int NUMBER = 1000000;

    private static final int CONNECTION_NUMBER = 60;

    private static final String[] nameArray = new String[]{"张三", "李四", "学习", "浩然", "萌新", "哈皮"};

    private static final Integer[] ageArray = new Integer[]{4, 10, 20, 16, 17, 12};

    private static Connection[] connectList = new Connection[CONNECTION_NUMBER];

    private static PreparedStatement[] preparedStatementList = new PreparedStatement[CONNECTION_NUMBER];

    public static void main(String[] args) throws Exception {
        init();
        insertUse();
        shutdown();
    }

    public static void init() throws Exception {
        for (int i = 0; i < CONNECTION_NUMBER; i++) {
            Connection connection = JDBCUtil.getConnection();
            String sql = "insert into tb_user(name, age) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connectList[i] = connection;
            preparedStatementList[i] = preparedStatement;
        }
    }

    public static PreparedStatement getPreparedStatement(int index) {
        return preparedStatementList[index % CONNECTION_NUMBER];
    }

    public static void insertUse() throws Exception {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(60);
        CountDownLatch countDownLatch = new CountDownLatch(NUMBER);
        for (int i = 0; i < NUMBER; i++) {
            final int finalI = i;
            executorService.submit(() -> {
                PreparedStatement preparedStatement = getPreparedStatement(finalI);
                int index = finalI % nameArray.length;
                try {
                    preparedStatement.setString(1, nameArray[index]);
                    preparedStatement.setInt(2, ageArray[index]);
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        System.out.println("用户表插入" + NUMBER + "条数据所需时间：" + costTime + " ms");
    }

    private static void shutdown() throws Exception {
        for (PreparedStatement statement : preparedStatementList) {
            statement.close();
        }
        for (Connection connection : connectList) {
            connection.close();
        }
    }
}
