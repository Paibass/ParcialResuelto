package ar.edu.unlam.pb2.Parcial01;

public class Producto extends Item{
	
	private Integer puntoDeReposicion;
	
	public Producto(Integer codigo, String nombre, Double precio, Integer puntoDeReposicion) {
		super(codigo, nombre, precio);
		// TODO Auto-generated constructor stub
		this.puntoDeReposicion = puntoDeReposicion;
	}

	public Integer getPuntoDeReposicion() {
		return puntoDeReposicion;
	}

	public void setPuntoDeReposicion(Integer puntoDeReposicion) {
		this.puntoDeReposicion = puntoDeReposicion;
	}
	
	
}
