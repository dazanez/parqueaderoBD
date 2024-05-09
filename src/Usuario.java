import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
    static String nombreTabla = "usuario";
    static String defaultPass = "Contra1!";
    String username;
    String pass;
    Timestamp createdAt;
    Timestamp lastLogin;

    public Usuario(String username) {
        this.username = username;
    }

    public Usuario(String username, String pass) {
        this.username = username.length() > 2 ? username : "defaultUser";

        // Asignacion de contrasena valida
        if (validatePass(pass)) this.pass = pass;
        else {
            this.pass = defaultPass;
            System.out.println("La contraseña no cumple con los requsitos, se estableció la contraseña por defecto");
        }
    }

    public Usuario(String username, String pass, Timestamp createdAt, Timestamp lastLogin) {
        this.username = username.length() > 2 ? username : "defaultUser";
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;

        // Asignacion de contrasena válida
        if (validatePass(pass)) this.pass = pass;
        else {
            this.pass = defaultPass;
            System.out.println("La contraseña no cumple con los requsitos, se estableció la contraseña por defecto");
        }
    }

    public static Usuario getUsuario(String username, String pass) {
        String query = String.format("SELECT usuario FROM %s WHERE (usuario = '%s' AND pass = '%s')", nombreTabla, username, pass);
        boolean autenticado = false;

        try {
            autenticado = Conexion.executeQuery(query);
        }
        catch (SQLException err) {
            System.out.println("Ocurrió un error al intentar obtener el usuario");
            System.out.println(err.getMessage());
        }
        return autenticado ? new Usuario(username) : null;
    }

    public static boolean validatePass(String pass) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_+=])(?=.{8,})[a-zA-Z0-9!@#$%^&*()-_+=]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    public boolean registerUser() {
        return registerUser(username, pass);
    }
    public static boolean registerUser(String username, String pass) {
        String query = String.format("INSERT INTO %s(usuario, pass) " +
                "VALUES ('%s', '%s');", nombreTabla, username, pass);
        int rowsAffected = 0;
        try {
            System.out.println(query);
            rowsAffected = Conexion.insertData(query);
        } catch (SQLException err) {
            System.out.println("Ocurrió un error registrando el nuevo usuario");
            System.out.println(err.getMessage());
        }
        return rowsAffected == 1;
    }
}
