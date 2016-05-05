package sako.fabio.nasa.discovery.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Classe que representa um ID
 * 
 * @author fabio
 *
 * @param <T> Tipo que este ID irá representar
 */
@JacksonXmlRootElement(localName="identify")
public class Identify<T> {
	private T id;

	/**
	 * Construtor
	 * 
	 * @param id  recebe o valor do ID
	 */
	@JsonCreator
	public Identify(@JsonProperty("id") T id) {
		super();
		this.id = id;
	}

	/**
	 * Recupera o valor do ID
	 * 
	 * @return Identificador
	 */
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName="id")
	public T getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Identify<?> other = (Identify<?>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Identify [id=" + id + "]";
	}

}
