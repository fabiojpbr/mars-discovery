package sako.fabio.nasa.rest.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sako.fabio.nasa.discovery.manager.interfaces.DiscoveryManagerInterface;
import sako.fabio.nasa.discovery.model.Identify;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.model.Probe;
import sako.fabio.nasa.rest.convert.PlateauResourceAssembler;
import sako.fabio.nasa.rest.convert.ProbeResourceAssembler;
import sako.fabio.nasa.rest.resources.PlateauResource;
import sako.fabio.nasa.rest.resources.ProbeResource;

/**
 * Classe Controller para gerenciar a exploração de Marte
 * @author fabio
 *
 */
@RestController
@RequestMapping("/mars-discovery")
public class DiscoveryManagerController {
	@Autowired
	private DiscoveryManagerInterface discoveryManager;
	@Autowired
	private PlateauResourceAssembler plateauResourceAssembler;
	@Autowired
	private ProbeResourceAssembler probeResourceAssembler;

	/**
	 * Serviço para criação/configuração do Planalto
	 * @param plateau Recebe os dados do Planalto
	 * @return
	 */
	@ApiOperation(value = "Criar Planalto", nickname = "Criar Planalto")
	@RequestMapping(method = RequestMethod.POST, path = "/plateau", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = Plateau.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 409, message = "Conflit, There is already a plateau") })
	@ResponseBody
	public ResponseEntity<Void> createPlateau(@RequestBody Plateau plateau) {
		discoveryManager.setPlateau(plateau);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(methodOn(getClass()).getPlateau()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Busca o Planalto configurado
	 * @return Planalto criado/configurado
	 */
	@ApiOperation(value = "obter Planalto", nickname = "obter Planalto")
	@RequestMapping(method = RequestMethod.GET, path = "/plateau", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Plateau.class),
			@ApiResponse(code = 404, message = "Not Found") })
	@ResponseBody
	public PlateauResource getPlateau() {
		Plateau plateau = discoveryManager.getPlateau();
		return plateauResourceAssembler.toResource(plateau);
	}

	/**
	 * Adiciona a Sonda no Planalto
	 * @param probe Planalto
	 * @return
	 */
	@ApiOperation(value = "Adiciona Sonda", nickname = "Adiciona Sonda")
	@RequestMapping(method = RequestMethod.POST, path = "/plateau/probe", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = Plateau.class),
			@ApiResponse(code = 409, message = "Conflit") })
	@ResponseBody
	public ResponseEntity<Void> createProbe(@RequestBody Probe probe) {
		Probe probeAdded = discoveryManager.addProbe(probe.getName(), probe.getCoordination(), probe.getDirection());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(methodOn(getClass()).getProbe(probeAdded.getName().getId())).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * Executa os comandos da Sonda
	 * @param name Nome da Sonda que realizará os movimentos
	 * @param commands Lista de Comandos
	 * @return
	 */
	@ApiOperation(value = "Executar comandos", nickname = "Executar comandos")
	@RequestMapping(method = RequestMethod.PUT, path = "/plateau/probe/{name}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 409, message = "Conflit") })
	@ResponseBody
	public ProbeResource executeCommandProbe(@PathVariable("name") String name,
			@RequestBody(required = true) Collection<String> commands) {
		Probe probe = discoveryManager.executeCommand(new Identify<String>(name), commands);
		return probeResourceAssembler.toResource(probe);
	}

	/**
	 * Obter a lista de Sondas configurado no Planalto
	 * @return Sondas que estão no Planalto
	 */
	@ApiOperation(value = "obter Sondas", nickname = "obter Sondas")
	@RequestMapping(method = RequestMethod.GET, path = "/plateau/probe", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@ResponseBody
	public List<ProbeResource> getProbes() {
		Collection<Probe> probes = discoveryManager.getProbes();
		return probeResourceAssembler.toResources(probes);
	}

	/**
	 * Obter a Sonda através do seu nome
	 * @param name Nome da Sonda
	 * @return
	 */
	@ApiOperation(value = "obter Sonda", nickname = "obter Sonda")
	@RequestMapping(method = RequestMethod.GET, path = "/plateau/probe/{name}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Probe.class),
			@ApiResponse(code = 404, message = "Not Found") })
	@ResponseBody
	public ProbeResource getProbe(@PathVariable("name") String name) {
		Probe probe = discoveryManager.findProbeByName(new Identify<String>(name));
		return probeResourceAssembler.toResource(probe);
	}
	
	/**
	 * Remove a sonda através do seu nome
	 * @param name Nome da sonda que será removida
	 * @return
	 */
	@ApiOperation(value = "Remover Sonda", nickname = "Remover Sonda")
	@RequestMapping(method = RequestMethod.DELETE, path = "/plateau/probe/{name}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@ResponseBody
	public ResponseEntity<Void> deleteProbe(@PathVariable("name") String name) {
		discoveryManager.deleteProbeByName(new Identify<String>(name));
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
