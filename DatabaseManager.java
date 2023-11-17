public class DatabaseManager {

    private String username;
    private String password;
    private String databaseURL;
    private Connection con;

    protected void createConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(databaseURL, username, PasswordSec.decrypt(password));
            if (con.isValid(5)) {
                SQLQuery.username = username;
                SQLQuery.password = password;
                SQLQuery.databaseURL = databaseURL;
            }
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new SqlToJsonError("Error creating connection or performing SQL operation", e, username, databaseURL);
        }
    }
}

class SqlToJsonError extends RuntimeException {

    private String username;
    private String databaseURL;

    public SqlToJsonError(String message, Throwable cause, String username, String databaseURL) {
        super(message, cause);
        this.username = username;
        this.databaseURL = databaseURL;
    }

    public String getUsername() {
        return username;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }
}
