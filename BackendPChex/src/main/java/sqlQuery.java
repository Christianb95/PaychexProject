import java.sql.*;
import java.util.ArrayList;


public class sqlQuery {

    public static ArrayList<Object>query_builder(Connection con, String query){
        /*  Input: Connection to database, String query, and String name (tax name, tax_id, or location_id)
        Output: ArrayList of query information
        builds query as prepared statement, and returns query info */
        ArrayList<Object> query_info = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(query); //prepares query and then name is inserted to replace ?
//            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery(); //executes SQL query and returns result set
            ResultSetMetaData metaData = rs.getMetaData(); //gets metadata from result set to use as keys
            int column_count = metaData.getColumnCount(); //gets column count from metadata for iteration
            //adds to array to return info above
            query_info.add(rs);
            query_info.add(column_count);
            query_info.add(metaData);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return query_info;
    }

}

