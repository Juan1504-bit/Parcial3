package co.edu.uniquindio.poo.parcial3.controller;

import co.edu.uniquindio.poo.parcial3.model.Cajero;
import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;
import co.edu.uniquindio.poo.parcial3.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.parcial3.model.PanaderoIndustrial;
import co.edu.uniquindio.poo.parcial3.model.Pedido;
import co.edu.uniquindio.poo.parcial3.model.TipoPan;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa intermedia entre la vista y el modelo.
 * La vista nunca toca la clase Panaderia directamente.
 */
public class PanaderiaController {

    public static final String ARTESANAL = "Panadero artesanal";
    public static final String INDUSTRIAL = "Panadero industrial";
    public static final String CAJERO = "Cajero";
    public static final List<String> TIPOS_EMPLEADO = List.of(ARTESANAL, INDUSTRIAL, CAJERO);

    private final Panaderia panaderia;

    public PanaderiaController() {
        this.panaderia = new Panaderia();
    }

    public Empleado registrarEmpleado(String tipo, String nombre, String documento,
                                      String codigo, int minutos, String caja) {
        if (tipo == null) {
            throw new IllegalArgumentException("Seleccione el tipo de empleado.");
        }
        if (nombre == null || nombre.isBlank() || documento == null || documento.isBlank()
                || codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Nombre, documento y codigo son obligatorios.");
        }
        if (minutos < 0) {
            throw new IllegalArgumentException("Los minutos no pueden ser negativos.");
        }

        Empleado empleado;
        switch (tipo) {
            case ARTESANAL -> empleado = new PanaderoArtesanal(nombre, documento, codigo, minutos);
            case INDUSTRIAL -> empleado = new PanaderoIndustrial(nombre, documento, codigo, minutos);
            case CAJERO -> {
                if (caja == null || caja.isBlank()) {
                    throw new IllegalArgumentException("Indique la caja asignada al cajero.");
                }
                empleado = new Cajero(nombre, documento, codigo, minutos, caja);
            }
            default -> throw new IllegalArgumentException("Tipo de empleado desconocido.");
        }
        panaderia.registrarEmpleado(empleado);
        return empleado;
    }

    public Pedido registrarPedido(String codigo, String cliente, TipoPan tipoPan, int cantidad) {
        return panaderia.registrarPedido(codigo, cliente, tipoPan, cantidad);
    }

    public Pedido asignarPedido(String codigoPedido) {
        return panaderia.asignarPedido(codigoPedido);
    }

    public Pedido cancelarPedido(String codigoPedido) {
        return panaderia.cancelarPedido(codigoPedido);
    }

    public ArrayList<Empleado> getEmpleados() {
        return panaderia.getListaEmpleados();
    }

    public ArrayList<Pedido> getPedidos() {
        return panaderia.getListaPedidos();
    }

    /** Datos de ejemplo para la demostracion (los mismos escenarios del parcial). */
    public void cargarDatosEjemplo() {
        registrarSiNoExiste(ARTESANAL, "Ana", "1001", "E01", 100, null);
        registrarSiNoExiste(ARTESANAL, "Diana", "1002", "E02", 80, null);
        registrarSiNoExiste(INDUSTRIAL, "Carlos", "1003", "E03", 120, null);
        registrarSiNoExiste(CAJERO, "Lucia", "1004", "E04", 120, "Caja 1");
    }

    private void registrarSiNoExiste(String tipo, String nombre, String doc, String cod, int min, String caja) {
        if (panaderia.buscarEmpleado(cod) == null) {
            registrarEmpleado(tipo, nombre, doc, cod, min, caja);
        }
    }
}
