package co.edu.uniquindio.poo.parcial3.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanaderiaTest {

    private Panaderia panaderia;

    @BeforeEach
    void setUp() {
        panaderia = new Panaderia(); 
    }

    @Test
    void registrarPedidoValidoQuedaPendiente() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "1", "E01", 100);
        panaderia.registrarEmpleado(ana);

        Pedido p = panaderia.registrarPedido("P01", "Cafe Sol", TipoPan.TRADICIONAL, 25);

        assertEquals(EstadoPedido.PENDIENTE, p.getEstado());
        assertEquals(25, p.getCantidad());
        assertEquals(1, panaderia.getListaPedidos().size());
        assertEquals(100, ana.getMinutosDisponibles());
    }

    @Test
    void registrarCodigoRepetidoTrasCancelarLanzaExcepcion() {
        panaderia.registrarPedido("P01", "Cafe Sol", TipoPan.TRADICIONAL, 10);
        panaderia.cancelarPedido("P01");

        assertThrows(IllegalArgumentException.class,
                () -> panaderia.registrarPedido("P01", "Otro", TipoPan.INTEGRAL, 5));

        assertEquals(1, panaderia.getListaPedidos().size());
        assertEquals(EstadoPedido.CANCELADO, panaderia.buscarPedido("P01").getEstado());
    }

    @Test
    void registrarPedidoConCantidadCeroLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> panaderia.registrarPedido("P01", "Cafe Sol", TipoPan.TRADICIONAL, 0));

        assertTrue(panaderia.getListaPedidos().isEmpty());
    }


    @Test
    void asignarEligeAlPanaderoQueTardaMenos() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "1", "E01", 100);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "2", "E02", 45);
        panaderia.registrarEmpleado(ana);
        panaderia.registrarEmpleado(carlos);
        panaderia.registrarPedido("P01", "Cafe Sol", TipoPan.TRADICIONAL, 25);

        Pedido p = panaderia.asignarPedido("P01");

        assertEquals(EstadoPedido.ASIGNADO, p.getEstado());
        assertEquals("E02", p.getCodigoEmpleado());
        assertEquals(45, p.getMinutosReservados());
        assertEquals(0, carlos.getMinutosDisponibles());
        assertEquals(100, ana.getMinutosDisponibles());
    }

    @Test
    void asignarEmpateSeResuelvePorOrdenDeRegistro() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "1", "E01", 80);
        PanaderoArtesanal diana = new PanaderoArtesanal("Diana", "2", "E02", 80);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "3", "E03", 54);
        panaderia.registrarEmpleado(ana);
        panaderia.registrarEmpleado(diana);
        panaderia.registrarEmpleado(carlos);
        panaderia.registrarPedido("P01", "Cafe Sol", TipoPan.INTEGRAL, 13);

        Pedido p = panaderia.asignarPedido("P01");

        assertEquals("E01", p.getCodigoEmpleado());
        assertEquals(80, p.getMinutosReservados());
        assertEquals(0, ana.getMinutosDisponibles());
        assertEquals(80, diana.getMinutosDisponibles());
        assertEquals(54, carlos.getMinutosDisponibles());
    }
}
