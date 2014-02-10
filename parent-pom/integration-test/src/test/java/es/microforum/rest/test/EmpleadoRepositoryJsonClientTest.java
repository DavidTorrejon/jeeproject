package es.microforum.rest.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import es.microforum.model.Empleado;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-data-app-context.xml" })
@ActiveProfiles("integration_test")
public class EmpleadoRepositoryJsonClientTest {

	private static final Logger logger = LoggerFactory.getLogger(EmpleadoRepositoryJsonClientTest.class);
	
	private final String empleadoUrl = "http://localhost:8080/service-frontend-0.0.3-SNAPSHOT/empleado";

	private String acceptHeaderValue = "application/json";

	@Autowired
	ApplicationContext context;

	private JdbcTemplate jdbcTemplate;

	RestTemplate restTemplate = new RestTemplate();

	@Before
	public void before() {
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
		
	@Test
	public void getTestNombre() {
		// Empleado con dni:1111 y nombre 1111
		try {
			Resource<Empleado> resource = getEmpleado(new URI(
					"http://localhost:8080/service-frontend-0.0.3-SNAPSHOT/empleado/1111"));
			assertTrue(resource.getContent().getNombre().equals("1111"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSave() {
		logger.info("Test Save - ");

		jdbcTemplate.execute("DELETE FROM empleado where dni='prueba'");

		try {
			final URI empleadoUri = new URI(empleadoUrl);
			final String empresaUrl11 = "http://localhost:8080/service-frontend-0.0.3-SNAPSHOT/empresa/11"; 
			
			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> mediaTypes = new ArrayList<MediaType>();
			mediaTypes.add(MediaType.valueOf(acceptHeaderValue));
			requestHeaders.setAccept(mediaTypes);
			requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue));
			HttpMethod post = HttpMethod.POST;
			
			String bodyEmpleado = "{\"dni\":\"prueba\",\"nombre\":\"prueba\",\"direccion\":\"prueba\",\"empresa\":[{\"rel\":\"nif\",\"href\":\"" + empresaUrl11 + "\"}],\"version\":\"1\"}";

			HttpEntity<String> entity = new HttpEntity<String>(bodyEmpleado,
					requestHeaders);

			ResponseEntity<String> response = restTemplate.exchange(empleadoUri,
					post, entity, String.class);

			assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));

			jdbcTemplate.execute("DELETE FROM empleado where dni='prueba'");

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testListado() {
		logger.info("Test Listado - ");
		try {
			final URI empleadoUri = new URI(empleadoUrl);
			Resource<List<Resource<Empleado>>> empleados = getEmpleados(empleadoUri);
			assertNotNull(empleados.getContent());
			assertTrue(empleados.getContent().size()!= 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testModificar() {
		logger.info("Test Modificar - ");

		jdbcTemplate
				.execute("INSERT INTO empleado(`dni`, `nombre`, `direccion`, `nif`, `version`) VALUES ('prueba','prueba','prueba','11',1);");

		String url = empleadoUrl + "/prueba";

		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.valueOf(acceptHeaderValue));
		requestHeaders.setAccept(mediaTypes);
		requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue));
		HttpMethod put = HttpMethod.PUT;

		String body = "{\"nombre\":\"Nombre Modificado\"}";
		HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);

		ResponseEntity<String> response = restTemplate.exchange(url, put,
				entity, String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
		int count = jdbcTemplate
				.queryForInt("select count(*) from empleado where nombre='Nombre Modificado'");
		assertTrue(count == 1);
		
		jdbcTemplate.execute("DELETE FROM empleado where dni='prueba'");
	}

	@Test
	public void testDelete() {
		logger.info("Test Delete - ");

		jdbcTemplate
		.execute("INSERT INTO empleado(`dni`, `nombre`, `direccion`, `nif`, `version`) VALUES ('prueba','prueba','prueba','11',1);");

		try {
			restTemplate.delete(empleadoUrl + "/prueba");
			int count = jdbcTemplate
					.queryForInt("select count(*) from empleado where dni='prueba'");
			assertTrue(count == 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	private Resource<Empleado> getEmpleado(URI uri) {
		return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Empleado>>() {
		}).getBody();

	}

	private Resource<List<Resource<Empleado>>> getEmpleados(URI uri) {
		return restTemplate
				.exchange(
						uri,
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<Resource<List<Resource<Empleado>>>>() {
						}).getBody();
	}

}
