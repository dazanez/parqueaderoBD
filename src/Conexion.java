import java.sql.*;
import java.util.Objects;

public class Conexion {
    static String BD = "parqueadero";
    static String PORT = "5433";
    static String USER = "postgres";
    static String HOST = "localhost";
    static String PASS = "12345";
    static Connection connection = null;


    public static void main(String[] args) throws SQLException {
        try {
            executeQuery("SELECT * FROM ticket;");
        }
        catch (SQLException err) {
            err.printStackTrace();
            System.out.print("Ocurrió un error durante la consulta: " + err.getMessage());
        }
    }

    public static Connection connectToDatabase() {
        Connection newConnection = null;
        try {
            Class.forName("org.postgresql.Driver"); // Verifica que exista el driver de postgresl
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        // Database connect
        // Conectamos con la base de datos
        try {
            newConnection = DriverManager.getConnection(
                    "jdbc:postgresql://" + HOST + ":" + PORT +"/" + BD, USER, PASS);
            System.out.println("¡Se ha establecido la conexión con la base de datos correctamente!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newConnection;
    }

    public static boolean executeQuery(String sqlQuery, String[] showValues) throws SQLException {
        /* Ex: executeQuery("SELECT * FROM ticket;"); */
        connection = connectToDatabase();
        Statement stmt; // Objeto de tipo sentencia SQL
        ResultSet rs;
        boolean emptyQuery = true;

        stmt = connection.createStatement();
        rs = stmt.executeQuery(sqlQuery);

        if (rs == null) {
            // Si no hay nada en la tabla retorna null
            // Se cierra conexion a la BD
            System.out.println("No se ha encontrado nungún registro para la consulta: " + sqlQuery);
        }

        else {
            String toPrint = "";
            showValues = (showValues == null | Objects.equals(showValues, new String[]{})) ? getColumnsNames(rs) : showValues;

            for (String value : showValues) System.out.print(value + " | "); // Imprime los nombres de las columnas
            System.out.println();

            while (rs.next()) {
                emptyQuery = false;
                // Se obtienen datos de las tablas
                // Se crea un objeto con los datos obtenidos de la DB
                for (String value : showValues) toPrint += rs.getString(value) + " | ";
                toPrint += "\n";
            }
            System.out.println(toPrint);
            rs.close();
        }
        stmt.close();
        // Se cierra conexion a la BD
        connection.close();
        // Se retorna toda la coleccion

        return !emptyQuery;
    }

    public static boolean executeQuery(String sqlQuery) throws SQLException {
        return executeQuery(sqlQuery, null);
    }

    public static String[] getColumnsNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int numColumns = metaData.getColumnCount();
        String[] columnsNames = new String[numColumns];
        System.out.println("getColumnsNames(). Length columnsNames: " + columnsNames.length);

        for (int i = 1; i <= numColumns; i++) {
            columnsNames[i-1] = metaData.getColumnName(i); // getColumnName empieza idx en 1
//            System.out.print("Columna: " + metaData.getColumnName(i) + " - ");
//            System.out.println(columnsNames[i-1]);
        }
        System.out.println();

        return columnsNames;
    }

    public static int insertData(String sql) throws SQLException {
        connection = connectToDatabase();
        Statement stmt = connection.createStatement();
        int rowsAffected;

        rowsAffected = stmt.executeUpdate(sql);
        stmt.close();
        connection.close();
        return rowsAffected;
    }
}
