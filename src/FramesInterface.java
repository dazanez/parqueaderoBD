import javax.lang.model.util.ElementScanner6;
import javax.swing.*;

public class FramesInterface {
    static Usuario currentUser;
    public static void mainInterface() {
        userLogInSignUpInterface();
    }

    public static void userLogInSignUpInterface() {
        int opcion = showUserMenu();

        while (opcion != 3 || (currentUser == null && opcion != 3)){
            switch (opcion) {
                case 1:
                    currentUser = signUpUserFrame();
                    if (currentUser != null) {
                        boolean usuarioRegistrado = currentUser.registerUser();
                        if (usuarioRegistrado) {
                            JOptionPane.showMessageDialog(null, "El usuario se ha registrado");
                        }
                        else JOptionPane.showMessageDialog(null, "Ocurrio un error en el registro, intenta de nuevo");
                    }
                    else JOptionPane.showMessageDialog(null, "El usuario no se ha registrado");
                    break;
                case 2:
                    currentUser = LogInUserFrame();
                    if (currentUser != null)
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso", "Inicio de sesión", JOptionPane.PLAIN_MESSAGE);
                    else JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Inicio de sesión no válido", JOptionPane.ERROR_MESSAGE);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo del Programa...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Selecciona una opción válida", "Opción no válida", JOptionPane.ERROR_MESSAGE);
            }
            opcion = showUserMenu();
        }
    }

    public static int showUserMenu() {
        String[] opciones = {"Registrar Usuario", "Ingresar", "Salir"};
        return JOptionPane.showOptionDialog(
                null,
                "Selecciona una opción:",
                "Menu de usuarios",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]) + 1;
    }

    public static Usuario LogInUserFrame() {
        JFrame frame = new JFrame();

        JTextField usuarioField = new JTextField(12);
        JPasswordField passField = new JPasswordField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre de usuario:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passField);

        int opcion;

        while (true) {
            opcion = JOptionPane.showConfirmDialog(
                    frame,
                    panel,
                    "Inicio de sesión",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );
            String username = usuarioField.getText();
            String pass = passField.getText();

            if (opcion == JOptionPane.OK_OPTION) {
                if (Usuario.getUsuario(username, pass) != null) {
                    System.out.println("Inicio de sesión exitoso, usuario: " + username);
                    return new Usuario(username);
                }
                else JOptionPane.showMessageDialog(frame,
                        "Usuario o contraseña no válidos, intenta de nuevo",
                        "Inicio de sesión incorrecto",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Inicio de sesión cancelado");
                return null;
            }
        }
    }

    public static Usuario signUpUserFrame() {
        JFrame frame = new JFrame();

        JTextField usuarioField = new JTextField(12);
        JPasswordField passField = new JPasswordField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre de usuario:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passField);

        int opcion;

        while (true) {
            opcion = JOptionPane.showConfirmDialog(
                    frame,
                    panel,
                    "Registro de nuevo usuario",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );
            String username = usuarioField.getText();
            String pass = passField.getText();

            if (opcion == JOptionPane.OK_OPTION) {
                if (username.length() > 1 && Usuario.validatePass(pass)) {
                    return new Usuario(username, pass);
                }
                else if (!Usuario.validatePass(pass))
                    JOptionPane.showMessageDialog(
                            frame,
                            "La contraseña no es válida. Debe contener 8 caracteres, 1 mayúscula, 1 minúscula, 1 número y 1 caracter especial",
                            "Contraseña no válida",
                            JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(frame,
                            "El nombre de usuario no es válido. Debe tener al menos 3 caracteres",
                            "Nombre de usuario no válido",
                            JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(frame,"Registro cancelado");
                return null;
            }
        }
    }
}
