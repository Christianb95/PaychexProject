import java.lang.reflect.Array;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

/*TODO: store SQL results, convert to format to nest easily?, convert to JSON
 */
public class toJSON {
    public static void main(String[] args){
        /*
         *
         */
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        //<editor-fold desc="User and Password">
        String user = "";
        String password = "";
        //</editor-fold>
        try {
            Class.forName(
                    "com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println(con);
        }
        catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        boolean dummy_export = true;//results of export button
//        Object[] sqlResults = new Object[100];/*possible store SQL results*/
//        StringBuilder sqlStr;//results of sql query. May alter based on SQL fetch class
//
//        sqlResults[0] = "dummy";
//        sqlResults[1] = 0;
//        sqlStr = convertSQL(sqlResults);
//
//        displayResults(sqlStr);
//
//        if (dummy_export)
//            writeJSON(sqlStr);
//    }
//    public static StringBuilder convertSQL(Object[] sqlResults){
//        StringBuilder convertedSQL= null;
//        String word;
//        for (int i=0; i < sqlResults.length; i++){
//            word = sqlResults[i].toString();
//            convertedSQL.append(word);
//        }
//        return convertedSQL;
//    }
//    public static void writeJSON(StringBuilder results){
//     /* Writes retrieved information to JSON file*/
//    }
//    public static void displayResults(StringBuilder results){
//        /*Display results on React screen*/
    }
}
