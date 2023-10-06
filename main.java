public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";

        DatabaseManager dbManager = new DatabaseManager(url, username, password);

        String query = "SELECT * FROM your_table";
        ResultSet resultSet = dbManager.executeQuery(query);

        try {
            while (resultSet.next()) {
                // Access data using resultSet.getXXX() methods
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // Process the data as needed
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbManager.closeConnection();
    }
}
