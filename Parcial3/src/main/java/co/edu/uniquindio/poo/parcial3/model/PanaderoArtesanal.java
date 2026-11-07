package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoArtesanal extends Panadero {

    private static final int CAPACIDAD_BANDEJA = 12;
    private static final int MIN_TRADICIONAL = 30;
    private static final int MIN_INTEGRAL = 40;

    public PanaderoArtesanal(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);
    }

    @Override
    public int calcularMinutos(Pedido pedido) {
        int bandejas = (pedido.getCantidad() + CAPACIDAD_BANDEJA - 1) / CAPACIDAD_BANDEJA;
        int minutosPorBandeja = pedido.getTipoPan() == TipoPan.TRADICIONAL ? MIN_TRADICIONAL : MIN_INTEGRAL;
        return bandejas * minutosPorBandeja;
    }

    @Override
    public String getTipo() {
        return "Panadero artesanal";
    }
}
