package sako.fabio.nasa.rest.convert;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.rest.controllers.DiscoveryManagerController;
import sako.fabio.nasa.rest.resources.PlateauResource;

@Component
public class PlateauResourceAssembler extends ResourceAssemblerSupport<Plateau, PlateauResource> {

	public PlateauResourceAssembler() {
		super(DiscoveryManagerController.class, PlateauResource.class);
	}

	@Override
	public PlateauResource toResource(Plateau entity) {
		PlateauResource plateauResource = new PlateauResource(entity.getHeight(), entity.getWidth());
		plateauResource.add(linkTo(methodOn(DiscoveryManagerController.class).getPlateau()).withSelfRel());
		plateauResource.add(linkTo(methodOn(DiscoveryManagerController.class).getProbes()).withRel("probes"));
		return plateauResource;
	}

}
