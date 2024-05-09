import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {
        FramesInterface.mainInterface();
        try {
            System.out.println("\n" + "=".repeat(100));
//            Vehiculo.createNewVehiculo("AXM335", TipoVehiculo.CARRO);
//            Ticket.createNewTicket("AXM335", Timestamp.valueOf("2024-05-01 00:00:00"), null);
//            System.out.println("\n\n");
//
//            Ticket.getTicketsByDate("2024-05-01");
//            System.out.println("\n\n");
//
//            System.out.println("Costo guardado del vehiculo: "
//                    + Ticket.calcular_valor(Timestamp.valueOf("2024-05-01 00:00:00"),
//                    Timestamp.valueOf("2024-05-01 22:50:00"),
//                    TipoVehiculo.CARRO));

//            Ticket.getAllTickets();
            Vehiculo.getVehiculoByPlaca("DXD60D");
/*            Ticket.getAllTickets();
            Ticket.getTicketsByDate("2023-07-20");
            System.out.println("Se creo el nuevo registro"
                    + Ticket.createNewTicket("EXM12E", Timestamp.valueOf("2023-03-20 00:00:00"), Timestamp.valueOf("2023-03-22 00:00:00")));
*/

        }
        catch (Exception err) {
            System.out.println("Hubo un error en la consulta");
            err.printStackTrace();
        }
    }
}
