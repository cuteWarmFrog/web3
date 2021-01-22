package com.example.lab3.Models;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@ManagedBean
@Named("nodeDao")
@ApplicationScoped
public class NodeDAO implements Serializable {

    /*
     @Resource(lookup = "java:jboss/datasources/PostgresDS")
     private DataSource ds;
    */

    private Connection connection;

    public NodeDAO() {
        connect();
    }

    private void connect() {
        String url;
        String user;
        String password;
        try {
            Class.forName("org.postgresql.Driver");
            Properties prop = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
            prop.load(inputStream);

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.username");
            password = prop.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addNode(Node node) {
        connect();
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Node> getNodes() {
        ResultSet rs;
        PreparedStatement pst;
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
