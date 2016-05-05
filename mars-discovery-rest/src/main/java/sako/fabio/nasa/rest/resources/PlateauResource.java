package sako.fabio.nasa.rest.resources;

import org.springframework.hateoas.ResourceSupport;
/**
 * Classe que permite usar Hateous para o recurso Plateau
 * @author fabio
 *
 */
public class PlateauResource extends ResourceSupport {
	private int depth;
	private int width;
	
	public PlateauResource(int depth, int width) {
		super();
		this.depth = depth;
		this.width = width;
	}

	public int getDepth() {
		return depth;
	}

	public int getWidth() {
		return width;
	}
	
	
}
