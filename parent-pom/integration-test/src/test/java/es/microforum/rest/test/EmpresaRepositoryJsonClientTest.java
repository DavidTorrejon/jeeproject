package es.microforum.rest.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.context.ApplicationContext;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.microforum.model.Empresa;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-data-app-context.xml" })
@ActiveProfiles("integration_test")
public class EmpresaRepositoryJsonClientTest {

	private static final Logger logger = LoggerFactory
			.getLogger(EmpresaRepositoryJsonClientTest.class);

	private final String empresaUrl = "http://localhost:8080/service-frontend-0.0.3-SNAPSHOT/empresa";

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
		// Empresa con nif:11 y nombre Empresa1
		try {
			Resource<Empresa> resource = getEmpresa(new URI(
					"http://localhost:8080/service-frontend-0.0.3-SNAPSHOT/empresa/11"));
			assertTrue(resource.getContent().getNombre().equals("Empresa1"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSave() {
		logger.info("Test Save - ");

		jdbcTemplate.execute("DELETE FROM empresa where nif='4'");

		try {
			final URI empresaUri = new URI(empresaUrl);

			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> mediaTypes = new ArrayList<MediaType>();
			mediaTypes.add(MediaType.valueOf(acceptHeaderValue));
			requestHeaders.setAccept(mediaTypes);
			requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue));
			HttpMethod post = HttpMethod.POST;

			String bodyEmpresa = "{\"nif\":\"4\",\"direccionFiscal\":\"dir4\",\"fechaInicioActividades\":\"1390851622000\",\"nombre\":\"Empresa4\"}";

			HttpEntity<String> entity = new HttpEntity<String>(bodyEmpresa,
					requestHeaders);

			ResponseEntity<String> response = restTemplate.exchange(empresaUri,
					post, entity, String.class);

			assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));

			jdbcTemplate.execute("DELETE FROM empresa where nif='4'");

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testListado() {
		logger.info("Test Listado - ");
		try {
			final URI empresaUri = new URI(empresaUrl);
			Resource<List<Resource<Empresa>>> empresas = getEmpresas(empresaUri);
			assertNotNull(empresas.getContent());
			assertTrue(empresas.getContent().size()!= 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testModificar() {
		logger.info("Test Modificar - ");

		jdbcTemplate
				.execute("INSERT INTO empresa(`nif`, `nombre`, `direccionFiscal`, `fechaInicioActividades`, `version`) VALUES ('4','Empresa4','dir4','2014-01-28',1);");

		String url = empresaUrl + "/4";

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
				.queryForInt("select count(*) from empresa where nombre='Nombre Modificado'");
		assertTrue(count == 1);
		jdbcTemplate.execute("DELETE FROM empresa where nif='4'");
	}

	@Test
	public void testDelete() {
		logger.info("Test Delete - ");

		jdbcTemplate
				.execute("INSERT INTO empresa(`nif`, `nombre`, `direccionFiscal`, `fechaInicioActividades`, `version`) VALUES ('4','Empresa4','dir4','2014-01-28',1);");

		try {
			restTemplate.delete(empresaUrl + "/4");
			int count = jdbcTemplate
					.queryForInt("select count(*) from empresa where nif='4'");
			assertTrue(count == 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	private Resource<Empresa> getEmpresa(URI uri) {
		return restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<Resource<Empresa>>() {
				}).getBody();
	}

	private Resource<List<Resource<Empresa>>> getEmpresas(URI uri) {
		return restTemplate
				.exchange(
						uri,
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<Resource<List<Resource<Empresa>>>>() {
						}).getBody();
	}
}
