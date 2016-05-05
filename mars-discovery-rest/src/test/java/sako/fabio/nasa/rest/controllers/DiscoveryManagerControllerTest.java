package sako.fabio.nasa.rest.controllers;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sako.fabio.nasa.discovery.enums.Command.L;
import static sako.fabio.nasa.discovery.enums.Command.M;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sako.fabio.nasa.configuration.Application;
import sako.fabio.nasa.discovery.enums.Command;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.enums.Status;
import sako.fabio.nasa.discovery.manager.DiscoveryManager;
import sako.fabio.nasa.discovery.model.CommandExecution;
import sako.fabio.nasa.discovery.model.Coordination;
import sako.fabio.nasa.discovery.model.Identify;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.model.Probe;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest()
public class DiscoveryManagerControllerTest {

	private static final String FORMAT_CONTEXT = "/mars-discovery/%s";

	private static final String PROPE_1_NAME = "Spirit";
	private static final String PROPE_2_NAME = "Opportunity";
	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private DiscoveryManager discoveryManager;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		Plateau plateau = new Plateau(5, 5);
		Probe probe = new Probe(new Identify<String>(PROPE_1_NAME), new Coordination(0, 0), Direction.N);
		discoveryManager.deletePlateau();
		discoveryManager.setPlateau(plateau);
		for (Probe p : discoveryManager.getProbes()) {
			discoveryManager.deleteProbeByName(p.getName());
		}
		discoveryManager.addProbe(probe.getName(), probe.getCoordination(), probe.getDirection());
	}

	@Test
	public void testCreatePlateau() throws IOException, Exception {
		discoveryManager.deletePlateau();
		Plateau plateau = new Plateau(5, 5);
		mockMvc.perform(post(format(FORMAT_CONTEXT, "plateau"))
				.content(this.json(plateau)).contentType(contentType))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testCreatePlateauIllegalValues() throws IOException, Exception {
		discoveryManager.deletePlateau();
		Plateau plateau = new Plateau(0, 0);
		mockMvc.perform(post(format(FORMAT_CONTEXT, "plateau"))
				.content(this.json(plateau)).contentType(contentType))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreatePlateauAlreadyExists() throws IOException, Exception {
		Plateau plateau = new Plateau(5, 5);
		mockMvc.perform(post(format(FORMAT_CONTEXT, "plateau")).content(this.json(plateau))
				.contentType(contentType)).andExpect(status().isConflict());
	}

	@Test
	public void testGetPlateau() throws IOException, Exception {
		mockMvc.perform(get(format(FORMAT_CONTEXT, "plateau")).contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.width", is(5)))
				.andExpect(jsonPath("$.depth", is(5)))
				;
	}
	
	@Test
	public void testGetPlateauNotFound() throws IOException, Exception {
		discoveryManager.deletePlateau();
		mockMvc.perform(get(format(FORMAT_CONTEXT, "plateau")).contentType(contentType))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testCreateProbe() throws IOException, Exception{
		Probe probe = new Probe(new Identify<String>(PROPE_2_NAME), new Coordination(1, 1), Direction.E);
		mockMvc.perform(post(format(FORMAT_CONTEXT, "plateau/probe")).content(json(probe)).contentType(contentType)).andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void testCreateProbeAlreadyExistsSameName() throws IOException, Exception{
		Probe probe = new Probe(new Identify<String>(PROPE_1_NAME), new Coordination(1, 1), Direction.E);
		mockMvc.perform(post(format(FORMAT_CONTEXT, "plateau/probe")).content(json(probe)).contentType(contentType)).andExpect(MockMvcResultMatchers.status().isConflict());
	}
	
	@Test
	public void testCreateProbeAlreadyExistsInPosition() throws IOException, Exception{
		Probe probe = new Probe(new Identify<String>(PROPE_2_NAME), new Coordination(0, 0), Direction.E);
		mockMvc.perform(post(format(FORMAT_CONTEXT, "plateau/probe")).content(json(probe)).contentType(contentType)).andExpect(MockMvcResultMatchers.status().isConflict());
	}
	
	@Test
	public void testExecuteCommandProbe() throws IOException, Exception{
		Identify<String> name = new Identify<String>(PROPE_1_NAME);
		List<String> commands = Arrays.asList("M","M","R","M");
		mockMvc.perform(put(format(FORMAT_CONTEXT, "plateau/probe/"+name.getId()))
				.content(json(commands)).contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.coordination.x", is(1)))
				.andExpect(jsonPath("$.coordination.y", is(2)))
				.andExpect(jsonPath("$.direction", is(Direction.E.name())))
				;
	}
	
	@Test
	public void testExecuteCommandProbeBorderInvasion() throws IOException, Exception{
		Identify<String> name = new Identify<String>(PROPE_1_NAME);
		List<String> commands = Arrays.asList("M","M","L","M");
		mockMvc.perform(put(format(FORMAT_CONTEXT, "plateau/probe/"+name.getId()))
				.content(json(commands)).contentType(contentType))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void testExecuteCommandProbeInvalidCommand() throws IOException, Exception{
		Identify<String> name = new Identify<String>(PROPE_1_NAME);
		List<String> commands = Arrays.asList("X","M","L","M");
		mockMvc.perform(put(format(FORMAT_CONTEXT, "plateau/probe/"+name.getId()))
				.content(json(commands)).contentType(contentType))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testExecuteCommandProbeBusyPlace() throws IOException, Exception{
		Probe probe = new Probe(new Identify<String>(PROPE_2_NAME), new Coordination(1, 2), Direction.E);
		discoveryManager.addProbe(probe.getName(), probe.getCoordination(), probe.getDirection());
		Identify<String> name = new Identify<String>(PROPE_1_NAME);
		List<String> commands = Arrays.asList("M","M","L","M");
		
		mockMvc.perform(put(format(FORMAT_CONTEXT, "plateau/probe/"+name.getId()))
				.content(json(commands)).contentType(contentType))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void testExecuteCommandExecutionProbe() throws IOException, Exception{
		Identify<String> nameProbe = new Identify<String>(PROPE_1_NAME);
		Identify<String> nameProbe2 = new Identify<String>(PROPE_2_NAME);
		Identify<String> nameNotFound = new Identify<String>("NotExist");
		Probe probe2 = new Probe(nameProbe2, new Coordination(1, 2), Direction.E);
		discoveryManager.addProbe(probe2.getName(), probe2.getCoordination(), probe2.getDirection());
		Collection<CommandExecution<Identify<String>, String>> commandExecutions = new ArrayList<>();
		commandExecutions.add(new CommandExecution<Identify<String>, String>(nameProbe, Arrays.asList(M,L)));
		commandExecutions.add(new CommandExecution<Identify<String>, String>(nameNotFound, Arrays.asList(M,L)));
		commandExecutions.add(new CommandExecution<Identify<String>, String>(nameProbe2, Arrays.asList(L,L,M,M)));
		mockMvc.perform(put(format(FORMAT_CONTEXT, "plateau/probe"))
				.content(json(commandExecutions)).contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].status", is(Status.OK.name())))
				.andExpect(jsonPath("$[1].status", is(Status.NOT_FOUND.name())))
				.andExpect(jsonPath("$[2].status", is(Status.ERROR.name())))
				;
	}
	
	@Test
	public void getProbe() throws Exception{
		Identify<String> name = new Identify<String>(PROPE_1_NAME);
		mockMvc.perform(get(format(FORMAT_CONTEXT, "plateau/probe/"+name.getId()))
				.contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.coordination.x", is(0)))
				.andExpect(jsonPath("$.coordination.y", is(0)))
				.andExpect(jsonPath("$.direction", is(Direction.N.name())));
				
	}
	
	@Test
	public void getProbesOneProbe() throws Exception{
		mockMvc.perform(get(format(FORMAT_CONTEXT, "plateau/probe"))
				.contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].coordination.x", is(0)))
				.andExpect(jsonPath("$[0].coordination.y", is(0)))
				.andExpect(jsonPath("$[0].direction", is(Direction.N.name())));
			
	}
	
	@Test
	public void getProbesTwoProbe() throws Exception{
		Probe probe = new Probe(new Identify<String>(PROPE_2_NAME), new Coordination(1, 1), Direction.E);
		discoveryManager.addProbe(probe.getName(), probe.getCoordination(), probe.getDirection());
		mockMvc.perform(get(format(FORMAT_CONTEXT, "plateau/probe"))
				.contentType(contentType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[1].coordination.x", is(1)))
				.andExpect(jsonPath("$[1].coordination.y", is(1)))
				.andExpect(jsonPath("$[1].direction", is(Direction.E.name())));
			
	}
	
	@Test
	public void getProbeNotFound() throws Exception{
		Identify<String> name = new Identify<String>(PROPE_2_NAME);
		mockMvc.perform(get(format(FORMAT_CONTEXT, "plateau/probe/"+name.getId()))
				.contentType(contentType))
		.andExpect(status().isNotFound());
				
	}
	
	@Test
	public void deleteProbe() throws Exception{
		Identify<String> name = new Identify<String>(PROPE_1_NAME);
		mockMvc.perform(delete(format(FORMAT_CONTEXT, "plateau/probe/"+name.getId()))
				.contentType(contentType))
		.andExpect(status().isNoContent());
				
	}
	
	@Test
	public void deleteProbeNotFound() throws Exception{
		Identify<String> name = new Identify<String>(PROPE_2_NAME);
		mockMvc.perform(delete(format(FORMAT_CONTEXT, "plateau/probe/"+name.getId()))
				.contentType(contentType))
		.andExpect(status().isNotFound());
				
	}
	
	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}
}
