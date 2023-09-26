import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.reflect.Array;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

public class main {
    public static void main(String[] args) {
        /*
         *
         */
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        //<editor-fold desc="Username and Password">
        String user = "";
        String password = "";
        //</editor-fold>

        try {
            Class.forName(
                    "oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

//            System.out.println(con);
//            System.out.println(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
