package sako.fabio.nasa.rest.resources;

import org.springframework.hateoas.ResourceSupport;
/**
 * Classe que permite usar Hateous para o recurso Plateau
 * @author fabio
 *
 */
public class PlateauResource extends ResourceSupport {
	private String name;
	private int height;
	private int width;
	
	public PlateauResource(String name,int depth, int width) {
		super();
		this.name = name;
		this.height = depth;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getName() {
		return name;
	}
	
	
}
