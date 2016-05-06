package sako.fabio.nasa.rest.convert;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import sako.fabio.nasa.discovery.enums.Status;
import sako.fabio.nasa.discovery.model.CommandExecution;
import sako.fabio.nasa.rest.controllers.DiscoveryManagerController;
import sako.fabio.nasa.rest.resources.CommandExecutionResource;

@Component
/**
 * Converte a classe Plateau para PlateauResource
 * @author fabio
 *
 */
public class CommandExecutionResourceAssembler extends ResourceAssemblerSupport<CommandExecution, CommandExecutionResource> {

	public CommandExecutionResourceAssembler() {
		super(DiscoveryManagerController.class, CommandExecutionResource.class);
	}

	@Override
	public CommandExecutionResource toResource(CommandExecution entity) {
		CommandExecutionResource resource = new CommandExecutionResource(entity.getPlateuName(), entity.getProbeName(), entity.getStatus(), entity.getMessage());
		if(!entity.getStatus().equals(Status.NOT_FOUND)){
			resource.add(linkTo(methodOn(DiscoveryManagerController.class).getProbe(entity.getPlateuName().getId(),entity.getProbeName().getId())).withSelfRel());
		}
		return resource;
	}



}
