package sako.fabio.nasa.discovery.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que representa as coordenadas do eixo X e Y
 * @author fabio
 *
 */
public class Coordination {
	private int x;
	private int y;
	/**
	 * Construtor 
	 * @param x
	 * @param y
	 */
	@JsonCreator
	public Coordination(@JsonProperty("x") int x, @JsonProperty("y") int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Método para obter o valor de x
	 * @return
	 */
	public int getX() {
		return x;
	}
	/**
	 * Método para obter o valor de y
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordination other = (Coordination) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordination [x=" + x + ", y=" + y + "]";
	}
	
	
	
}
