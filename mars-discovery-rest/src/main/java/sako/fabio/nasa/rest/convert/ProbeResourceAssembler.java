package sako.fabio.nasa.rest.convert;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import sako.fabio.nasa.discovery.model.Probe;
import sako.fabio.nasa.rest.controllers.DiscoveryManagerController;
import sako.fabio.nasa.rest.resources.ProbeResource;
@Component
/**
 * Converte a classe Probe para ProbeResource
 * @author fabio
 *
 */
public class ProbeResourceAssembler extends ResourceAssemblerSupport<Probe, ProbeResource> {

	public ProbeResourceAssembler() {
		super(DiscoveryManagerController.class, ProbeResource.class);
	}

	@Override
	public ProbeResource toResource(Probe entity) {
		ProbeResource probeResource = new ProbeResource(entity.getName().getId(),entity.getCoordination(), entity.getDirection());
		probeResource.add(linkTo(methodOn(DiscoveryManagerController.class).getProbe(entity.getPlateau().getName().getId(), entity.getName().getId())).withSelfRel());
		probeResource.add(linkTo(methodOn(DiscoveryManagerController.class).deleteProbe(entity.getPlateau().getName().getId(),entity.getName().getId())).withRel("delete"));
		probeResource.add(linkTo(methodOn(DiscoveryManagerController.class).executeCommandProbe(entity.getPlateau().getName().getId(),entity.getName().getId(), new ArrayList<>())).withRel("commands"));
		return probeResource;
	}

}
