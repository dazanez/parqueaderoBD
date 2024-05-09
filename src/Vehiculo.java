import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Vehiculo {
    static String nombreTabla = "Vehiculo";
    String placa;
    TipoVehiculo tipoVehiculo;
    String modelo;
    int anio;
    String color;

    public Vehiculo(String placa, TipoVehiculo tipoVehiculo) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
    }

    public Vehiculo(String placa, TipoVehiculo tipoVehiculo, String modelo, int anio, String color) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
    }

    public static void getVehiculoByPlaca(String placa) {
        System.out.println("========= GET BY PLACA =========");

        String query = "SELECT * FROM " + nombreTabla + " WHERE (placa = " + "'" + placa + "'" + ")";
        try {
            Conexion.executeQuery(query);
        } catch (SQLException err) {
            System.out.println("Ocurrio un error obteniendo el carro con placa " + placa);
            System.out.println(err.getMessage());
        }
    }

    public static boolean createNewVehiculo(String placa, TipoVehiculo tipoVehiculo) {
        System.out.println("========= CREATE VEHICULO =========");

        String query = String.format("INSERT INTO %s(placa, tipo_vehiculo)" +
                " VALUES ('%s', '%s');", nombreTabla, placa, tipoVehiculo);
        int rowsAffected = 0;

        try {
            System.out.println(query);
            rowsAffected = Conexion.insertData(query);
        }
        catch (SQLException err) {
            System.out.println("Ocurrio un error insertando el nuevo vehiculo");
            err.printStackTrace();
        }

//        String q = "INSERT INTO public.ticket(" +
//                "id_vehiculo, fecha_ingreso, fecha_salida, valor) " +
//                "VALUES ('" + placa + "', '" + fechaIngreso + "', " + + ", ") + " +
//                "VALUES ('DXD60D', '2023-07-20 12:00:00', '2023-08-07 12:00:00', NULL);";
        return rowsAffected == 1;
    }
}
