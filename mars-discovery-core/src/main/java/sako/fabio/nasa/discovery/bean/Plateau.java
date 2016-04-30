package sako.fabio.nasa.discovery.bean;

import java.util.HashMap;

import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;

public class Plateau{
	private int height;
	private int width;
	private HashMap<Coordination, Probe> mapElementCoordination;
	private HashMap<Identify<String>, Coordination> mapCoordinationKey;
	
	public Plateau(int height, int width) {
		super();
		//TODO Validar o tamanho
		this.height = height;
		this.width = width;
		this.mapElementCoordination = new HashMap<Coordination, Probe>();
		this.mapCoordinationKey = new HashMap<Identify<String>, Coordination>();
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void alterCoordination(Coordination coordination, Probe value) throws BordersInvasionException,BusyPlaceException{
		if(coordination.getY() >= 0 && coordination.getY() <= height && coordination.getX() >=0 && coordination.getX() <= width){
			if(mapElementCoordination.get(coordination) != null){
				throw new BusyPlaceException();
			}
			mapElementCoordination.remove(value.getCoordination());
			mapElementCoordination.put(coordination, value);
			
			mapCoordinationKey.remove(value.getId());
			mapCoordinationKey.put(value.getId(),coordination);
		}else{
			throw new BordersInvasionException();
		}
	}
	
	public Probe getProbeById(Identify<String> id){
		Coordination coordination = mapCoordinationKey.get(id);
		return mapElementCoordination.get(coordination);
	}
	
}
