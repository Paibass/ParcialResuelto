package ar.edu.unlam.pb2.Parcial01;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Venta {

	private String codigo;
	private Cliente cliente;
	private Vendedor vendedor;
	private Map<Vendible, Integer> renglones;

	public Venta(String codigo, Cliente cliente, Vendedor vendedor) {
		this.codigo = codigo;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.renglones = new HashMap<>();
	}

	public String getCodigo() {
		return this.codigo;
	}

	public Vendedor getVendedor() {
		return this.vendedor;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void agregarRenglon(Vendible vendible, Integer cantidad) {
		Integer cantidadParaActualizar = cantidad;
		if (this.renglones.containsKey(vendible)) {
			cantidadParaActualizar = this.renglones.get(vendible) + cantidad;
		}
		
		this.renglones.put(vendible, cantidadParaActualizar);
	}
	
	public Map<Vendible, Integer> getRenglones(){
		return this.renglones;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venta other = (Venta) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {

		return "Venta: " + codigo + "\n" + "Cliente" + cliente + "\n" + "Vendedor" + vendedor + "\n";
	}

}
