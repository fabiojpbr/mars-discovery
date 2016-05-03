package sako.fabio.nasa.rest.controllers;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sako.fabio.nasa.discovery.bean.Identify;
import sako.fabio.nasa.discovery.bean.Plateau;
import sako.fabio.nasa.discovery.bean.Probe;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;


@RestController
@RequestMapping("/mars-discovery")
public class ProbeManagerController extends ExceptionHandlingController{
	@Autowired
	private DiscoveryManagerInterface discoveryManager;
	
	@ApiOperation(value = "Criar Terreno", nickname = "Criar Terreno")
    @RequestMapping(method = RequestMethod.POST, path="/plateau", produces = {"application/json","application/xml"}, consumes= {"application/json","application/xml"})
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Created", response = Plateau.class),
            @ApiResponse(code = 409, message = "Conflit, Já existe um Terreno configurado")
            })
	@ResponseBody
	public ResponseEntity<Plateau> createPlateau(@RequestBody Plateau plateau){
		discoveryManager.setPlateau(plateau);
		return new ResponseEntity<Plateau>(plateau, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Criar", nickname = "Criar")
    @RequestMapping(method = RequestMethod.POST, path="/plateau/probe", produces = {"application/json","application/xml"}, consumes= {"application/json","application/xml"})
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Created", response = Plateau.class),
            @ApiResponse(code = 409, message = "Conflit")
            })
	@ResponseBody
	public Resource<Probe> createProbe(@RequestBody Probe probe){
		Probe probeAdded = discoveryManager.addProbe(probe.getId(), probe.getCoordination(), probe.getDirection());
		Resource<Probe> resource = new Resource<Probe>(probeAdded);
		resource.add(linkTo(methodOn(ProbeManagerController.class).getProbe(probe.getId().getId())).withSelfRel());
		return resource;
	}
	
	@ApiOperation(value = "Command", nickname = "Command")
    @RequestMapping(method = RequestMethod.PUT, path="/plateau/probe/{name}", produces = {"application/json","application/xml"}, consumes= {"application/json","application/xml"})
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Created", response = Plateau.class),
            @ApiResponse(code = 409, message = "Conflit")
            })
	@ResponseBody
	public ResponseEntity<Probe> commandProbe(@RequestParam("name") String name, @RequestBody Collection<String> commands){
		Probe probe = discoveryManager.command(new Identify<String>(name), commands);
		return new ResponseEntity<Probe>(probe, HttpStatus.OK);
	}
	
	@ApiOperation(value = "getProbes", nickname = "getProbes")
    @RequestMapping(method = RequestMethod.GET, path="/plateau/probe/", produces = {"application/json","application/xml"})
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Created", response = Plateau.class),
            @ApiResponse(code = 409, message = "Conflit")
            })
	@ResponseBody
	public ResponseEntity<Collection<Probe>> getProbes(){
		Collection<Probe> probes = discoveryManager.getProbes();
		return new ResponseEntity<Collection<Probe>>(probes, HttpStatus.OK);
	}
	@ApiOperation(value = "getProbe", nickname = "getProbe")
    @RequestMapping(method = RequestMethod.GET, path="/plateau/probe/{id}", produces = {"application/json","application/xml"})
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Probe.class),
            @ApiResponse(code = 409, message = "Conflit")
            })
	@ResponseBody
	public ResponseEntity<Probe> getProbe(@RequestParam("id") String name){
		Probe probe = discoveryManager.findProbeByName(new Identify<String>(name));
		return new ResponseEntity<Probe>(probe, HttpStatus.OK);
	}
}
