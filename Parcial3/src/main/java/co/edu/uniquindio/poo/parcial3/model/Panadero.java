package co.edu.uniquindio.poo.parcial3.model;

public abstract class Panadero extends Empleado {

    public Panadero(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);
    }

    public abstract int calcularMinutos(Pedido pedido);
}
