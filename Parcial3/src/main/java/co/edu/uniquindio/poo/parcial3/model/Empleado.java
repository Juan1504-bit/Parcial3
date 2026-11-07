package co.edu.uniquindio.poo.parcial3.model;

public abstract class Empleado {

    private String nombre;
    private int minutosDisponibles;
    private String codigo;
    private String documento;

    public Empleado(String nombre, String documento, String codigo, int minutosDisponibles) {
        this.nombre = nombre;
        this.documento = documento;
        this.codigo = codigo;
        this.minutosDisponibles = minutosDisponibles;
    }

    public boolean tieneTiempo(int minutos) {
        return minutosDisponibles >= minutos;
    }

    public void descontarMinutos(int minutos) {
        if (!tieneTiempo(minutos)) {
            throw new IllegalStateException("El empleado no tiene minutos suficientes.");
        }
        minutosDisponibles -= minutos;
    }

    public void devolverMinutos(int minutos) {
        minutosDisponibles += minutos;
    }

    public abstract String getTipo();

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getMinutosDisponibles() { return minutosDisponibles; }
    public void setMinutosDisponibles(int minutosDisponibles) { this.minutosDisponibles = minutosDisponibles; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + getTipo() + ", " + minutosDisponibles + " min)";
    }
}
