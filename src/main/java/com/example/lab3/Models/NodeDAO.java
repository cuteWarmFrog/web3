package com.example.lab3.Models;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.annotation.Resource;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NodeDAO implements Serializable {

    @Resource
    private HikariDataSource ds;

    public NodeDAO() {
        HikariConfig config = new HikariConfig();
        config.setUsername("user1");
        config.setPassword("user1");
        config.setJdbcUrl("jdbc:postgresql://localhost/testdb");
        ds = new HikariDataSource(config);
    }

    public void addNode(Node node) {
        Connection connection = getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("INSERT INTO \"newnodes\" " +
                    "(x, y, r, result, time)" +
                    " VALUES(?, ?, ?, ?, ?);");

            pst.setDouble(1, node.getX());
            pst.setDouble(2, node.getY());
            pst.setDouble(3, node.getR());
            pst.setBoolean(4, node.getResult());
            pst.setString(5, node.getCreateTime().toString());
            pst.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Node> getNodes() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection connection = getConnection();
        String stm = "Select * from \"newnodes\";";
        List<Node> records = new ArrayList<>();

        try {
            pst = connection.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while(rs.next()) {
                Node node = new Node();
                node.setX(rs.getDouble(2));
                node.setY(rs.getDouble(3));
                node.setR(rs.getDouble(4));
                node.setResult(rs.getBoolean(5));
                node.setCreateTime(LocalDateTime.parse(rs.getString(6)));
                records.add(node);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Connection getConnectionOld() {
        boolean isLocal = false;
        Connection con = null;

        String url;
        String user;
        String password;

        if(isLocal) {
            url = "jdbc:postgresql://localhost/testdb";
            user = "user1";
            password = "user1";
        } else {
            url = "jdbc:postgresql://pg:5432/studs";
            user = "s278069";
            password = "keq816";
        }

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection completed.");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return con;
    }
}
