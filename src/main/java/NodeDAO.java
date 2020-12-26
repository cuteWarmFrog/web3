import com.example.lab3.Models.Node;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NodeJPA implements Serializable {

    public NodeJPA() {}


    public void addNode(Node node) {
        StringBuilder query = new StringBuilder("INSERT INTO \"nodes\" VALUES(");
        query.append(node.getId()).append(",");
        query.append(node.getX()).append(",");
        query.append(node.getY()).append(",");
        query.append(node.getR()).append(",");
        query.append(node.getResult()).append(",");
        query.append("'").append(node.getCreateTime()).append("');");

        PreparedStatement pst;
        Connection connection = getConnection();
        try {
            pst = connection.prepareStatement(query.toString());
            pst.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        Connection con = null;
        String url = "jdbc:postgresql://localhost/testdb";
        String user = "user1";
        String password = "user1";

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
