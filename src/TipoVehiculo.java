public enum TipoVehiculo {
    CARRO("CARRO", 100),
    BICICLETA("BICICLETA", 10),
    MOTO("MOTO", 60),
    ;

    int tarifa;
    String nombre;

    TipoVehiculo(String nombre, int tarifa) {
        this.tarifa = tarifa;
        this.nombre = nombre;
    }

    public int getTarifa() {
        return tarifa;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
