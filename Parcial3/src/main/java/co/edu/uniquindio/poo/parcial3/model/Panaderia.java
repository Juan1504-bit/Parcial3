package co.edu.uniquindio.poo.parcial3.model;

import java.util.ArrayList;

public class Panaderia {

    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Pedido> listaPedidos;

    public Panaderia() {
        this.listaEmpleados = new ArrayList<>();
        this.listaPedidos = new ArrayList<>();
    }

    public void registrarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo.");
        }
        if (buscarEmpleado(empleado.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un empleado con el codigo " + empleado.getCodigo());
        }
        listaEmpleados.add(empleado);
    }

    public Empleado buscarEmpleado(String codigo) {
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getCodigo().equals(codigo)) {
                return empleado;
            }
        }
        return null;
    }

    public Pedido registrarPedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo es obligatorio.");
        }
        if (tipoPan == null) {
            throw new IllegalArgumentException("El tipo de pan es obligatorio.");
        }
        if (buscarPedido(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un pedido con el codigo " + codigo);
        }
        Pedido pedido = new Pedido(codigo, nombreCliente, tipoPan, cantidad);
        listaPedidos.add(pedido);
        return pedido;
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido pedido : listaPedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                return pedido;
            }
        }
        return null;
    }

    public Pedido asignarPedido(String codigoPedido) {
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("No existe un pedido con el codigo " + codigoPedido);
        }
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("El pedido no esta PENDIENTE (estado: " + pedido.getEstado() + ").");
        }

        Panadero elegido = null;
        int minutosElegido = 0;
        for (Empleado empleado : listaEmpleados) {
            if (empleado instanceof Panadero) {
                Panadero panadero = (Panadero) empleado;
                int minutos = panadero.calcularMinutos(pedido);
                if (panadero.tieneTiempo(minutos) && (elegido == null || minutos < minutosElegido)) {
                    elegido = panadero;
                    minutosElegido = minutos;
                }
            }
        }
        if (elegido == null) {
            throw new IllegalStateException("Ningun panadero tiene tiempo suficiente para el pedido.");
        }

        elegido.descontarMinutos(minutosElegido);
        pedido.asignar(elegido.getCodigo(), minutosElegido);
        return pedido;
    }

    public Pedido cancelarPedido(String codigoPedido) {
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("No existe un pedido con el codigo " + codigoPedido);
        }

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("El pedido ya esta CANCELADO.");
        }

        if (pedido.getEstado() == EstadoPedido.ASIGNADO) {
            Empleado empleado = buscarEmpleado(pedido.getCodigoEmpleado());
            if (empleado != null) {
                empleado.devolverMinutos(pedido.getMinutosReservados());
            }
        }

        pedido.cancelar();
        return pedido;
    }

    public ArrayList<Empleado> getListaEmpleados() { return listaEmpleados; }
    public ArrayList<Pedido> getListaPedidos() { return listaPedidos; }
}