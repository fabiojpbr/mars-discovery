package sako.fabio.nasa.rest.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sako.fabio.nasa.configuration.Application;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.manager.DiscoveryManager;
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
		discoveryManager.setPlateau(plateau);
		discoveryManager.addProbe(probe.getName(), probe.getCoordination(), probe.getDirection());
	}

	@Test
	public void testCreatePlateau() throws IOException, Exception {
		discoveryManager.deletePlateau();
		Plateau plateau = new Plateau(5, 5);
		mockMvc.perform(MockMvcRequestBuilders.post(String.format(FORMAT_CONTEXT, "plateau"))
				.content(this.json(plateau)).contentType(contentType))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void testCreatePlateauAlreadyExists() throws IOException, Exception {
		Plateau plateau = new Plateau(5, 5);
		mockMvc.perform(MockMvcRequestBuilders.post(String.format(FORMAT_CONTEXT, "plateau")).content(this.json(plateau))
				.contentType(contentType)).andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Test
	public void testGetPlateau() throws IOException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(String.format(FORMAT_CONTEXT, "plateau")).contentType(contentType))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.width", is(5)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.depth", is(5)))
				;
	}
	
	@Test
	public void testGetPlateauNotFound() throws IOException, Exception {
		discoveryManager.deletePlateau();
		mockMvc.perform(MockMvcRequestBuilders.get(String.format(FORMAT_CONTEXT, "plateau")).contentType(contentType))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testCreateProbe() throws IOException, Exception{
		Probe probe = new Probe(new Identify<String>(PROPE_2_NAME), new Coordination(1, 1), Direction.E);
		mockMvc.perform(MockMvcRequestBuilders.post(String.format(FORMAT_CONTEXT, "plateau/probe")).content(json(probe)).contentType(contentType)).andExpect(MockMvcResultMatchers.status().isCreated());
	}

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
