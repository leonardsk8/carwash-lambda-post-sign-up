package com.carwash.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.sql.*;

public class JDBCConnect {

    public static final String SUCCESSFULLY_EXECUTED_QUERY = "Successfully executed query. ";
    public static final String CAUGHT_EXCEPTION = "Caught exception: ";
    private final Context context;

    private static final String URL = "jdbc:mysql://database-1.cktiweavgcsr.us-east-1.rds.amazonaws.com:3306";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123*+";

    public JDBCConnect(Context context) {
        this.context = context;
    }

    public String updateUser(UserDTO userDTO) {
        LambdaLogger logger = context.getLogger();
        logger.log("Invoked JDBCSample.getCurrentTime");
        String result = "unavailable";
        String sql = "UPDATE carwash.user t SET t.email_confirmed = 1 WHERE t.email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,userDTO.getEmail());
            boolean execute = stmt.execute();
            logger.log(SUCCESSFULLY_EXECUTED_QUERY + execute);
        }catch (Exception e) {
        e.printStackTrace();
        logger.log(CAUGHT_EXCEPTION + e.getMessage());
    }
        return result;
    }
}