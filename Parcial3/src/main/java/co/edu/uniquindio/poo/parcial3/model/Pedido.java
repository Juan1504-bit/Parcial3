package co.edu.uniquindio.poo.parcial3.model;

public class Pedido {  private String codigo;
    private String nombreCliente;
    private TipoPan tipoPan;
    private int cantidad;
    private EstadoPedido estado;
    private String codigoEmpleado;
    private int minutosReservados;

    public Pedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        this.codigo = codigo;
        this.nombreCliente = nombreCliente;
        this.tipoPan = tipoPan;
        this.cantidad = cantidad;
        this.estado = EstadoPedido.PENDIENTE;
        this.codigoEmpleado = "";
        this.minutosReservados = 0;
    }

    public void asignar(String codigoEmpleado, int minutosReservados) {
        if (estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("Solo se puede asignar un pedido PENDIENTE.");
        }
        this.codigoEmpleado = codigoEmpleado;
        this.minutosReservados = minutosReservados;
        this.estado = EstadoPedido.ASIGNADO;
    }

    public void cancelar() {
        if (estado == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("El pedido ya esta CANCELADO.");
        }
        this.estado = EstadoPedido.CANCELADO;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public TipoPan getTipoPan() { return tipoPan; }
    public void setTipoPan(TipoPan tipoPan) { this.tipoPan = tipoPan; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }

    public int getMinutosReservados() { return minutosReservados; }
    public void setMinutosReservados(int minutosReservados) { this.minutosReservados = minutosReservados; }

    @Override
    public String toString() {
        return codigo + " - " + nombreCliente + " - " + tipoPan + " x" + cantidad + " [" + estado + "]";
    }

}
