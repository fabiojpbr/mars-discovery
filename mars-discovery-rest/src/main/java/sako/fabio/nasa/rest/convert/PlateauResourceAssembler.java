package sako.fabio.nasa.rest.convert;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import sako.fabio.nasa.discovery.bean.Plateau;
import sako.fabio.nasa.rest.controllers.ProbeManagerController;
import sako.fabio.nasa.rest.resources.PlateauResource;

@Component
public class PlateauResourceAssembler extends ResourceAssemblerSupport<Plateau, PlateauResource> {

	public PlateauResourceAssembler() {
		super(ProbeManagerController.class, PlateauResource.class);
	}

	@Override
	public PlateauResource toResource(Plateau entity) {
		PlateauResource plateauResource = new PlateauResource(entity.getHeight(), entity.getWidth());
		plateauResource.add(linkTo(methodOn(ProbeManagerController.class).getPlateau()).withSelfRel());
		plateauResource.add(linkTo(methodOn(ProbeManagerController.class).getProbes()).withRel("probes"));
		return plateauResource;
	}

}
