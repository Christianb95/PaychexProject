import java.lang.reflect.Array;

/*TODO: store SQL results, convert to format to nest easily?, convert to JSON
 */
public class toJSON {
    public static void main(String[] args){
        /*
         *
         */
        boolean dummy_export = true;//results of export button
        Object[] sqlResults = new Object[100];/*possible store SQL results*/
        StringBuilder sqlStr;//results of sql query. May alter based on SQL fetch class

        sqlResults[0] = "dummy";
        sqlResults[1] = 0;
        sqlStr = convertSQL(sqlResults);

        displayResults(sqlStr);

        if (dummy_export)
            writeJSON(sqlStr);
    }
    public static StringBuilder convertSQL(Object[] sqlResults){
        StringBuilder convertedSQL= null;
        String word;
        for (int i=0; i < sqlResults.length; i++){
            word = sqlResults[i].toString();
            convertedSQL.append(word);
        }
        return convertedSQL;
    }
    public static void writeJSON(StringBuilder results){
     /* Writes retrieved information to JSON file*/
    }
    public static void displayResults(StringBuilder results){
        /*Display results on React screen*/
    }
}
