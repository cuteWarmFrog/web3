package com.example.lab3.Models;

import com.example.lab3.Models.Node;
import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NodeDAO implements Serializable {

    public NodeDAO() {}

    public void addNode(Node node) {
        Connection connection = getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("INSERT INTO \"nodes\"" +
                    " VALUES(?, ?, ?, ?, ?, ?);");

            pst.setInt(1, node.getId());
            pst.setDouble(2, node.getX());
            pst.setDouble(3, node.getY());
            pst.setDouble(4, node.getR());
            pst.setBoolean(5, node.getResult());
            pst.setString(6, node.getCreateTime().toString());
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
        String stm = "Select * from \"nodes\";";
        List<Node> records = new ArrayList<>();

        try {
            pst = connection.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while(rs.next()) {
                Node node = new Node();
                node.setId(rs.getInt(1));
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
        boolean isLocal = true;
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
