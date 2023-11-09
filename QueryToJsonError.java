public class QueryToJsonError extends Exception {
    private String errorMessage;

    public QueryToJsonError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    public static void main(String[] args) {
        try {
            String query = "SELECT * FROM my_table";
            String result = convertQueryToJson(query);
            System.out.println("JSON result: " + result);
        } catch (QueryToJsonError e) {
            System.out.println("An error occurred while converting query to JSON: " + e.getMessage());
        }
    }

    public static String convertQueryToJson(String query) throws QueryToJsonError {

        if (query.isEmpty()) {
            throw new QueryToJsonError("Empty query");
        }

        try {
            ResultSet resultSet = executeQuery(query);

            String jsonResult = resultSetToJson(resultSet);

            return jsonResult;

        } catch (SQLException e) {
            throw new QueryToJsonError("Error executing the SQL query: " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String query) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet;
    }

    public static String resultSetToJson(ResultSet resultSet) throws SQLException {

        return "JSON result";
    }
}