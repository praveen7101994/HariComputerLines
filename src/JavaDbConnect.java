import java.sql.*;
import javax.swing.*;

import javax.swing.JOptionPane;


public class JavaDbConnect {
    private static Connection connection =null;
    
    public static Connection dbConnect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:db/HariComputerLines.db");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
