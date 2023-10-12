import java.sql.*;
import java.util.ArrayList;


public class sqlQuery {
//    public static Map<String, Object> tax(Connection con) {
//        /*  Input: Connection
//            Output: Linkedhash map with tax information for tax name given
//            Test SQL query
//            Passes query to builder, and gets results set, column number, and results set metaData back
//            Passes results set, column number, and results set metaData to map builder, and returns map
//         */
////        String name = "California Income Tax";
//        Scanner myObj = new Scanner(System.in);
//        String query = myObj.nextLine();
//        Map<String, Object>tax_info;
//        ArrayList<Object> query_results;
//        query_results= query_builder(con, query);
//        tax_info = toJSON.map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
//                (ResultSetMetaData) query_results.get(2));
//        return tax_info;
//    }
//    public static Map<String, Object> tax_rate(Connection con, Number tax_id){
//        /*  Input: Connection and Num tax id
//            Output: Linkedhash map of tax_rate_info table for tax id
//            Test tax_rate SQL query
//            Passes query to builder, and gets results set, column number, and results set metaData back
//            Passes results set, column number, and results set metaData to map builder, and returns map
//         */
//        String tax_rate_query = "select * from TAX_RATE WHERE TR_TAX_ID= ?";
//        Map <String, Object> tax_rate_info;
//        ArrayList<Object> query_results;
//        query_results= query_builder(con, tax_rate_query);
//        tax_rate_info = toJSON.map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
//                (ResultSetMetaData) query_results.get(2));
//        return tax_rate_info;
//    }
//    public static Map<String, Object> location(Connection con, Number location_id) {
//        /*  Input: Connection, and Num location id
//            Output: LinkedHash map with location information for location id
//            Test location SQL query
//            Passes query to builder, and gets results set, column number, and results set metaData back
//            Passes results set, column number, and results set metaData to map builder, and returns map
//         */
//        String location_query = "select * from LOCATION WHERE LOCATION_ID= ?";
//        Map <String, Object> location_info;
//        ArrayList<Object> query_results;
//        query_results= query_builder(con, location_query);
//        location_info = toJSON.map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
//                (ResultSetMetaData) query_results.get(2));
//        return location_info;
//    }

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

