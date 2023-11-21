package ar.edu.unlam.pb2.Parcial01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class TiendaTest {

	@Test(expected = VendedorDeLicenciaException.class)
	public void queAlIntentarAgregarUnaVentaParaUnVendedorDeLicenciaSeLanceUnaVendedorDeLicenciaException()
			throws VendedorDeLicenciaException {
		Cliente cliente = crearCliente("30-00000000-1", "Cliente 1");
		Vendedor vendedor = crearVendedor("40-00000000-1", "Carlos");
		vendedor.setDeLicencia(true);
		Venta venta = crearVenta("C-001",cliente, vendedor);
		Tienda tienda = crearTienda();
		tienda.agregarVenta(venta);
	}

	@Test(expected = VendibleInexistenteException.class)
	public void queAlIntentarAgregarUnVendibleInexistenteAUnaVentaSeLanceUnaVendibleInexistenteException()
			throws VendedorDeLicenciaException, VendibleInexistenteException {
		Cliente cliente = crearCliente("30-00000000-1", "Cliente 1");
		Producto producto = crearProducto(1, "Producto 1", 100D, 20);
		Vendedor vendedor = crearVendedor("40-00000000-1", "Carlos");
		Venta venta = crearVenta("C-001",cliente, vendedor);
		Tienda tienda = crearTienda();
		tienda.agregarVenta(venta);
		tienda.agregarProductoAVenta(venta.getCodigo(), producto);
	}

	@Test
	public void queSePuedaObtenerUnaListaDeProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion()
			throws VendibleInexistenteException {
		Tienda tienda = crearTienda();
		Producto producto1 = crearProducto(1, "Producto 1", 100D, 20);
		Producto producto2 = crearProducto(2, "Producto 2", 100D, 20);
		Producto producto3 = crearProducto(3, "Producto 3", 100D, 20);
		Producto producto4 = crearProducto(4, "Producto 4", 100D, 20);
		tienda.agregarProducto(producto1);
		tienda.agregarProducto(producto2);
		tienda.agregarProducto(producto3);
		tienda.agregarProducto(producto4);

		tienda.agregarStock(producto1, 10);
		tienda.agregarStock(producto2, 10);
		tienda.agregarStock(producto3, 50);
		tienda.agregarStock(producto4, 50);

		List<Producto> productos = tienda.obtenerProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion();

		assertEquals(productos.size(), 2);
	}

	@Test
	public void queSePuedaObtenerUnSetDeClientesOrdenadosPorRazonSocialDescendente() {
		Tienda tienda = crearTienda();
		Cliente cliente1 = crearCliente("30-00000000-1", "Cliente 1");
		Cliente cliente2 = crearCliente("30-00000000-2", "Cliente 2");
		Cliente cliente3 = crearCliente("30-00000000-3", "Cliente 3");
		tienda.agregarCliente(cliente1);
		tienda.agregarCliente(cliente2);
		tienda.agregarCliente(cliente3);

		List<Cliente> clientes = tienda.obtenerClientesOrdenadosPorRazonSocialDescendente();

		for (int i = 0; i < clientes.size() - 1; i++) {
			assertTrue(clientes.get(i).getRazonSocial().compareTo(clientes.get(i + 1).getRazonSocial()) > 0);
		}

	}

	@Test
	public void queSePuedaObtenerUnMapaDeVentasRealizadasPorCadaVendedor() throws VendibleInexistenteException, VendedorDeLicenciaException {
		// TODO: usar como key el vendedor y Set<Venta> para las ventas
		Tienda tienda = crearTienda();
		Vendedor carlos = crearVendedor("40-00000000-1", "Carlos");
		Vendedor juan = crearVendedor("40-00000000-2", "Juan");
		tienda.agregarVendedor(carlos);
		tienda.agregarVendedor(juan);
		
		Cliente cliente1 = crearCliente("30-00000000-1", "Cliente 1");
		
		Producto producto1 = crearProducto(1, "Producto 1", 100D, 20);
		Producto producto2 = crearProducto(2, "Producto 2", 100D, 20);
		Producto producto3 = crearProducto(3, "Producto 3", 100D, 20);
		Producto producto4 = crearProducto(4, "Producto 4", 100D, 20);
		tienda.agregarProducto(producto1);
		tienda.agregarProducto(producto2);
		tienda.agregarProducto(producto3);
		tienda.agregarProducto(producto4);

		tienda.agregarStock(producto1, 10);
		tienda.agregarStock(producto2, 10);
		tienda.agregarStock(producto3, 50);
		tienda.agregarStock(producto4, 50);
		
		Venta venta1 = crearVenta("C-0001",cliente1, carlos);
		Venta venta2 = crearVenta("C-0002",cliente1, juan);
		Venta venta3 = crearVenta("C-0003",cliente1, juan);
		Venta venta4 = crearVenta("C-0004",cliente1, juan);
		
		tienda.agregarVenta(venta1);
		tienda.agregarVenta(venta2);
		tienda.agregarVenta(venta3);
		tienda.agregarVenta(venta4);
		
		tienda.agregarProductoAVenta("C-0001", producto1);
		tienda.agregarProductoAVenta("C-0001", producto2);
		tienda.agregarProductoAVenta("C-0001", producto3);
		tienda.agregarProductoAVenta("C-0002", producto4);
		
		Map<Vendedor,Set<Venta>> ventasPorVendedor = tienda.obtenerVentasPorVendedor();
		
		assertEquals(ventasPorVendedor.get(carlos).size(), 1);
		assertEquals(ventasPorVendedor.get(juan).size(), 3);
	}

	@Test
	public void queSePuedaObtenerElTotalDeVentasDeServicios() throws VendedorDeLicenciaException, VendibleInexistenteException {
		Tienda tienda = crearTienda();
		Vendedor carlos = crearVendedor("40-00000000-1", "Carlos");
		Cliente cliente1 = crearCliente("30-00000000-1", "Cliente 1");
		
		Producto producto1 = crearProducto(1, "Producto 1", 500D, 20);
		Servicio servicio1 = crearServicio(2, "Servicio Técnico", 100d, "2023-02-01", "2023-03-01");
		Servicio servicio2 = crearServicio(3, "Servicio Técnico", 50d, "2023-02-01", "2023-03-01");
		Servicio servicio3 = crearServicio(4, "Servicio Técnico", 30d, "2023-02-01", "2023-03-01");
		
		tienda.agregarProducto(producto1);
		tienda.agregarServicio(servicio1);
		tienda.agregarServicio(servicio2);
		tienda.agregarServicio(servicio3);
		
		Venta venta1 = crearVenta("C-0001",cliente1, carlos);
		Venta venta2 = crearVenta("C-0002",cliente1, carlos);
		Venta venta3 = crearVenta("C-0003",cliente1, carlos);
		Venta venta4 = crearVenta("C-0004",cliente1, carlos);
		
		tienda.agregarVenta(venta1);
		tienda.agregarVenta(venta2);
		tienda.agregarVenta(venta3);
		tienda.agregarVenta(venta4);
		
		tienda.agregarProductoAVenta("C-0001", producto1);
		tienda.agregarServicioAVenta("C-0002", servicio1);
		tienda.agregarServicioAVenta("C-0003", servicio2);
		tienda.agregarServicioAVenta("C-0004", servicio3);
		
		Double totalDeVentasDeServicios = tienda.obtenerTotalDeVentasDeServicios();
		assertTrue(totalDeVentasDeServicios.equals(180D));
	}

	@Test
	public void queAlRealizarLaVentaDeUnProductoElStockSeActualiceCorrectamente() throws VendedorDeLicenciaException, VendibleInexistenteException {
		Tienda tienda = crearTienda();
		Vendedor carlos = crearVendedor("40-00000000-1", "Carlos");
		Cliente cliente1 = crearCliente("30-00000000-1", "Cliente 1");
		
		Producto producto1 = crearProducto(1, "Producto 1", 500D, 20);
		tienda.agregarProducto(producto1);
		tienda.agregarStock(producto1, 10);
		
		Venta venta1 = crearVenta("C-0001",cliente1, carlos);
		tienda.agregarVenta(venta1);
		tienda.agregarProductoAVenta("C-0001", producto1);
		Integer stock = tienda.getStock(producto1);
		assertTrue(stock.equals(9));
	}
	
	private Servicio crearServicio(Integer codigo, String nombre, Double precio, String fechaDeInicio, String fechaDeFinalizacion) {
		return new Servicio(codigo, nombre, precio, fechaDeInicio, fechaDeFinalizacion);
	}

	private Tienda crearTienda() {
		return new Tienda("20-30000000-5", "Mi tienda");
	}

	private Venta crearVenta(String codigo, Cliente cliente, Vendedor vendedor) {
		return new Venta(codigo, cliente, vendedor);
	}

	private Vendedor crearVendedor(String cuit, String nombre) {
		return new Vendedor(cuit, nombre);
	}

	private Producto crearProducto(Integer codigo, String nombre, Double precio, Integer puntoDeReposicion) {
		return new Producto(codigo, nombre, precio, puntoDeReposicion);
	}

	private Cliente crearCliente(String cuit, String razonSocial) {
		return new Cliente(cuit, razonSocial);
	}
}
