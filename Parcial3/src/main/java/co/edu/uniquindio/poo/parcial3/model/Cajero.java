package co.edu.uniquindio.poo.parcial3.model;

public class Cajero extends Empleado {

    private String caja;

    public Cajero(String nombre, String documento, String codigo, int minutosDisponibles, String caja) {
        super(nombre, documento, codigo, minutosDisponibles);
        this.caja = caja;
    }

    public String getCaja() { return caja; }
    public void setCaja(String caja) { this.caja = caja; }

    @Override
    public String getTipo() {
        return "Cajero";
    }
}
