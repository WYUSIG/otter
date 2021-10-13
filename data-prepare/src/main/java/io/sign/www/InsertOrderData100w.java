package io.sign.www;

import io.sign.www.util.JDBCUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InsertOrderData100w {

    private static final int NUMBER = 1000000;

    private static final int CONNECTION_NUMBER = 60;

    private static final String[] nameArray = new String[]{"可乐", "雪碧", "辣条", "糖果", "玩具", "杯子"};

    private static final String[] ageArray = new String[]{"4.1", "10", "3.88", "7.12", "3.21", "5"};

    private static Connection[] connectList = new Connection[CONNECTION_NUMBER];

    private static PreparedStatement[] preparedStatementList = new PreparedStatement[CONNECTION_NUMBER];

    public static void main(String[] args) throws Exception {
        init();
        insertOrder();
        shutdown();
    }

    public static void init() throws Exception {
        for (int i = 0; i < CONNECTION_NUMBER; i++) {
            Connection connection = JDBCUtil.getConnection();
            String sql = "insert into tb_order(good_name, price) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connectList[i] = connection;
            preparedStatementList[i] = preparedStatement;
        }
    }

    public static PreparedStatement getPreparedStatement(int index) {
        return preparedStatementList[index % CONNECTION_NUMBER];
    }

    public static void insertOrder() throws Exception {
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
                    preparedStatement.setBigDecimal(2, new BigDecimal(ageArray[index]));
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
        System.out.println("订单表插入" + NUMBER + "条数据所需时间：" + costTime + " ms");
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
