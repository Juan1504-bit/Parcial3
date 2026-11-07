package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoIndustrial extends Panadero {

    private static final int CAPACIDAD_LOTE = 30;
    private static final int PREPARACION_MAQUINA = 20;
    private static final int MIN_TRADICIONAL = 25;
    private static final int MIN_INTEGRAL = 35;

    public PanaderoIndustrial(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);
    }

    @Override
    public int calcularMinutos(Pedido pedido) {
        int lotes = (pedido.getCantidad() + CAPACIDAD_LOTE - 1) / CAPACIDAD_LOTE;
        int minutosPorLote = pedido.getTipoPan() == TipoPan.TRADICIONAL ? MIN_TRADICIONAL : MIN_INTEGRAL;
        return PREPARACION_MAQUINA + lotes * minutosPorLote;
    }

    @Override
    public String getTipo() {
        return "Panadero industrial";
    }
}
