package ar.edu.unlam.pb2.Parcial01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tienda {

	private String cuit;
	private String nombre;
	private Set<Vendible> vendibles;
	private Map<Producto, Integer> stock;
	private List<Cliente> clientes;
	private Set<Venta> ventas;
	private Set<Vendedor> vendedores;

	public Tienda(String cuit, String nombre) {
		super();
		this.cuit = cuit;
		this.nombre = nombre;
		this.vendibles = new HashSet<>();
		this.stock = new HashMap<>();
		this.clientes = new ArrayList<>();
		this.ventas = new HashSet<>();
		this.vendedores = new HashSet<>();
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Vendible getVendible(Integer codigo) throws VendibleInexistenteException {
		for (Vendible vendible : vendibles) {
			if (vendible.getCodigo().equals(codigo))
				return vendible;
		}

		throw new VendibleInexistenteException();
	}

	public void agregarProducto(Producto producto) {
		this.agregarProducto(producto, 0);
	}

	public void agregarServicio(Servicio servicio) {
		vendibles.add(servicio);
	}

	public void agregarProducto(Producto producto, Integer stockInicial) {
		vendibles.add(producto);
		stock.put(producto, stockInicial);
	}

	public Integer getStock(Producto producto) throws VendibleInexistenteException {
		return stock.get(producto);
	}

	public void agregarStock(Producto producto, Integer incremento) throws VendibleInexistenteException {
		Integer stock = this.getStock(producto);
		this.stock.put(producto, stock + incremento);
	}

	public Cliente getCliente(String cuit) {
		for (Cliente cliente : clientes) {
			if (cliente.getCuit() == cuit)
				return cliente;
		}
		return null;
	}

	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
	}

	public void agregarVenta(Venta venta) throws VendedorDeLicenciaException {
		if (venta.getVendedor().isDeLicencia()) {
			throw new VendedorDeLicenciaException();
		}
		ventas.add(venta);
	}

	public Producto obtenerProductoPorCodigo(Integer codigo) {
		for (Vendible v : this.vendibles) {
			if (v instanceof Producto && v.getCodigo().equals(codigo)) {
				return (Producto) v;
			}
		}
		return null;
	}

	public Venta getVenta(String codigo) {
		for (Venta venta : ventas) {
			if (venta.getCodigo().equals(codigo))
				return venta;
		}
		return null;
	}

	public Vendedor getVendedor(String dni) {
		for (Vendedor vendedor : vendedores) {
			if (vendedor.getDni() == dni)
				return vendedor;
		}
		return null;
	}

	public void agregarVendedor(Vendedor vendedor) {
		vendedores.add(vendedor);
	}

	public void agregarProductoAVenta(String codigoVenta, Producto producto) throws VendibleInexistenteException {
		Venta venta = this.getVenta(codigoVenta);

		if (this.obtenerProductoPorCodigo(producto.getCodigo()) == null) {
			throw new VendibleInexistenteException();
		}
		venta.agregarRenglon(producto, 1);
		Integer stock = this.getStock(producto);
		this.stock.put(producto, stock - 1);
	}
	
	public void agregarServicioAVenta(String codigoVenta, Servicio servicio) {
		Venta venta = this.getVenta(codigoVenta);
		venta.agregarRenglon(servicio, 1);
	}

	public List<Producto> obtenerProductosCuyoStockEsMenorOIgualAlPuntoDeReposicion() {
		List<Producto> productos = new ArrayList<>();

		for (Vendible v : this.vendibles) {
			if (v instanceof Producto) {
				Producto producto = (Producto) v;

				Integer stockActual = this.stock.get(producto);

				if (stockActual <= producto.getPuntoDeReposicion()) {
					productos.add(producto);
				}

			}
		}
		return productos;
	}

	public List<Cliente> obtenerClientesOrdenadosPorRazonSocialDescendente() {
		Collections.sort(this.clientes);
		return this.clientes;
	}

	public Map<Vendedor, Set<Venta>> obtenerVentasPorVendedor() {

		Map<Vendedor, Set<Venta>> ventasPorVendedor = new HashMap<>();
		for (Venta venta : this.ventas) {

			if (!ventasPorVendedor.containsKey(venta.getVendedor())) {
				ventasPorVendedor.put(venta.getVendedor(), new HashSet<>());
			}

			ventasPorVendedor.get(venta.getVendedor()).add(venta);

		}
		return ventasPorVendedor;
	}

	public Double obtenerTotalDeVentasDeServicios() {
		Double total = 0D;
		
		for(Venta venta : ventas) {
			Map<Vendible,Integer> renglones = venta.getRenglones();
			
			for(Vendible vendible : renglones.keySet()) {
				
				if(vendible instanceof Servicio) {
					total += vendible.getPrecio();
				}
			}
			
		}
		
		return total;

	}

	
}
