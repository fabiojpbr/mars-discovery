package sako.fabio.nasa.discovery.bean;

import java.util.Collection;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.exceptions.ObjectNasaNotFoundException;
/**
 * Classe que representa o Planalto
 * @author fabio
 *
 */
@JacksonXmlRootElement(localName="plateau")
public class Plateau{

	private int height;

	private int width;

	private HashMap<Coordination, Probe> mapElementCoordination;

	private HashMap<Identify<String>, Coordination> mapCoordinationKey;
	/**
	 * Construtor, no construtor define o tamanho do planalto a ser explorado
	 * @param height
	 * @param width
	 */
	@JsonCreator
	public Plateau(
			@JsonProperty("height")
			@JacksonXmlProperty(localName="height")
			int height,
			@JsonProperty("width")
			@JacksonXmlProperty(localName="width")
			int width) {
		super();
		//TODO Validar o tamanho
		this.height = height;
		this.width = width;
		this.mapElementCoordination = new HashMap<Coordination, Probe>();
		this.mapCoordinationKey = new HashMap<Identify<String>, Coordination>();
	}
	
	
	
	public Plateau() {
		super();
	}
	
	@JacksonXmlProperty(localName="height")
	public int getHeight() {
		return height;
	}
	
	@JacksonXmlProperty(localName="width")
	public int getWidth() {
		return width;
	}
	/**
	 * Método que altera a posição de uma sonda no planalto
	 * @param coordination Nova Coordenada
	 * @param value objeto que será movido
	 * @throws BordersInvasionException Exception ocorre quando o objeto ultrapassar o limite do planalto
	 * @throws BusyPlaceException Exception ocorre quando o objeto encontra no caminho outro objeto
	 */
	public void alterCoordination(Coordination coordination, Probe value) throws BordersInvasionException,BusyPlaceException{
		if(coordination.getY() >= 0 && coordination.getY() <= height && coordination.getX() >=0 && coordination.getX() <= width){
			if(mapElementCoordination.get(coordination) != null){
				throw new BusyPlaceException();
			}
			mapElementCoordination.remove(value.getCoordination());
			mapElementCoordination.put(coordination, value);
			
			mapCoordinationKey.remove(value.getName());
			mapCoordinationKey.put(value.getName(),coordination);
		}else{
			throw new BordersInvasionException(String.format("Probe: %s can not move", value.getName()));
		}
	}
	
	/**
	 * Retorna o objeto que está no planato de acordo com a sua identificação
	 * @param name Identificação do objeto que deseja recuperar
	 * @return
	 */
	public Probe getProbeByName(Identify<String> name){
		Coordination coordination = mapCoordinationKey.get(name);
		Probe probe = mapElementCoordination.get(coordination);
		return probe;
	}
	
	public void deleteProbeByName(Identify<String> name){
		Coordination coordination = mapCoordinationKey.remove(name);
		Probe probe = mapElementCoordination.remove(coordination);
		if(coordination == null || probe == null){
			throw new ObjectNasaNotFoundException(String.format("Probe: %s Not Found", name));
		}
	}
	
	@JsonIgnore
	@XmlTransient
	public Collection<Probe> getProbes(){
		return mapElementCoordination.values();
	}
	
}
