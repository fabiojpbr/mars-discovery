package sako.fabio.nasa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@ApiOperation(value = "Criar", nickname = "Criar")
    @RequestMapping(method = RequestMethod.POST, path="/plateau", produces = {"application/json","application/xml"}, consumes= {"application/json","application/xml"})
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Created", response = Plateau.class),
            @ApiResponse(code = 409, message = "Conflit, Já existe um planalto configurado")
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
	public ResponseEntity<Probe> createProbe(@RequestBody Probe probe){
		Probe probeAdded = discoveryManager.addProbe(probe.getId(), probe.getCoordination(), probe.getCardinalPoint());
		return new ResponseEntity<Probe>(probeAdded, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Command", nickname = "Command")
    @RequestMapping(method = RequestMethod.PUT, path="/plateau/probe/{name}", produces = {"application/json","application/xml"}, consumes= {"application/json","application/xml"})
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Created", response = Plateau.class),
            @ApiResponse(code = 409, message = "Conflit")
            })
	@ResponseBody
	public ResponseEntity<Probe> commandProbe(@RequestParam("name") String name, @RequestBody CommandList commands){
		Probe probe = discoveryManager.command(new Identify<String>(name), commands);
		return new ResponseEntity<Probe>(probe, HttpStatus.CREATED);
	}

}
