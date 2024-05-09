import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Ticket {
    Timestamp fechaIngreso;
    Timestamp fechaSalida;
    Vehiculo vehiculo;
    float valor;
    int numTicket;
    public static String nombreTabla = "ticket";
    static HashMap<TipoVehiculo, Integer> TARIFAS = new HashMap<>();


    public Ticket(Timestamp fechaIngreso, Vehiculo vehiculo) {
        this.fechaIngreso = fechaIngreso;
        this.vehiculo = vehiculo;
    }

    public Ticket(Timestamp fechaIngreso, Timestamp fechaSalida, Vehiculo vehiculo, float valor) {
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.vehiculo = vehiculo;
        this.valor = valor;
    }

    public float getValor() {
        valor = calcularMinutos(fechaIngreso, fechaSalida) * vehiculo.tipoVehiculo.getTarifa();
        return valor;
    }

    public static float calcular_valor(Timestamp fechaIngreso, Timestamp fechaSalida, TipoVehiculo tipoVehiculo) {
        int valor = calcularMinutos(fechaIngreso, fechaSalida) * tipoVehiculo.getTarifa();
        return valor;
    }

    private static int calcularMinutos(Timestamp fechaIngreso, Timestamp fechaSalida) {
        long milisegundos = fechaSalida.getTime() - fechaIngreso.getTime();
        int minutos = (int) TimeUnit.MILLISECONDS.toMinutes(milisegundos);
        return minutos;
    }

    public static boolean getAllTickets() throws SQLException {
        System.out.println("========= GET ALL =========");

        return Conexion.executeQuery("SELECT * FROM " + nombreTabla + ";");
    }
    public static void getTicketByNumTicket(int numTicket) {
        System.out.println("========= GET BY PLACA =========");

        String query = "SELECT * FROM " + nombreTabla + " WHERE (num_ticket = " + "'" + numTicket + "'" + ")";
        try {
            Conexion.executeQuery(query);
        } catch (SQLException err) {
            System.out.println("Ocurrio un error obteniendo el carro con placa " + numTicket);
            System.out.println(err.getMessage());
        }
    }

    /**
     * Ex: getTicketsByDate("2024-05-01")
     * @param dateStr String con formato de fecha YYYY-MM-DD
     */
    public static void getTicketsByDate(String dateStr) {
        System.out.println("========= GET BY DATE =========");

        Date date = Date.valueOf(dateStr);
        String  query = "SELECT * FROM " + nombreTabla + " WHERE (fecha_ingreso >= " + "'" + date + "'" + ")";
        try {
            Conexion.executeQuery(query);
        }
        catch (SQLException err) {
            System.out.println("Ocurrio un error consultando tickets por fecha");
            System.out.println(err.getMessage());
        }
    }

    public static boolean createNewTicket(String placa, Timestamp fechaIngreso, Timestamp fechaSalida) {
        System.out.println("========= CREATE =========");

        String fechaIngresoStr = fechaIngreso.toString();
        String fechaSalidaStr = fechaSalida != null ? String.format("'%s'", fechaSalida) : "NULL";

        String query = String.format("INSERT INTO %s(id_vehiculo, fecha_ingreso, fecha_salida)" +
                " VALUES ('%s', '%s', %s);", nombreTabla, placa, fechaIngresoStr, fechaSalidaStr);
        int rowsAffected = 0;

        try {
            System.out.println(query);
            rowsAffected = Conexion.insertData(query);
        }
        catch (SQLException err) {
            System.out.println("Ocurrio un error insertando el nuevo ticket");
            err.printStackTrace();
        }

//        String q = "INSERT INTO public.ticket(" +
//                "id_vehiculo, fecha_ingreso, fecha_salida, valor) " +
//                "VALUES ('" + placa + "', '" + fechaIngreso + "', " + + ", ") + " +
//                "VALUES ('DXD60D', '2023-07-20 12:00:00', '2023-08-07 12:00:00', NULL);";
        return rowsAffected == 1;
    }

    public static boolean deleteTicket(int ticket_num) {
        System.out.println("========= DELETE =========");

        String query = String.format("DELETE FROM %s WHERE num_ticket = %d" +
                " VALUES ('%s', '%s', %s);", nombreTabla, ticket_num);
        int rowsAffected = 0;

        try {
            System.out.println(query);
            rowsAffected = Conexion.insertData(query);
        }
        catch (SQLException err) {
            System.out.println("Ocurrio un error insertando el nuevo ticket");
            err.printStackTrace();
        }

//        String q = "INSERT INTO public.ticket(" +
//                "id_vehiculo, fecha_ingreso, fecha_salida, valor) " +
//                "VALUES ('" + placa + "', '" + fechaIngreso + "', " + + ", ") + " +
//                "VALUES ('DXD60D', '2023-07-20 12:00:00', '2023-08-07 12:00:00', NULL);";
        return rowsAffected == 1;
    }
}
