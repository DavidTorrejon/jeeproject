package es.microforum.rest.test;

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

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-data-app-context.xml"})
//@ActiveProfiles("integration_test")
public class EmpresaRepositoryJsonClientTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EmpresaRepositoryJsonClientTest.class);
	
	private final String empresaUrl = "http://localhost:8080/service-frontend-0.0.3-SNAPSHOT/empresa";
	
	private String acceptHeaderValue = "application/json";
	
	//@Autowired
	//ApplicationContext context;
	
	//private JdbcTemplate jdbcTemplate;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Before
	public void before() {
//		DataSource dataSource = (DataSource) context.getBean("dataSource");
//		jdbcTemplate = new JdbcTemplate(dataSource);
//		jdbcTemplate.execute("DELETE FROM subtipo_serie where cod_subtipo_ser=9999");
	}

	@Test
	public void getTestNombre() {
		//Empresa con nif:11 y nombre Empresa1
		try {
			Resource<Empresa> resource = getEmpresa(new URI("http://localhost:8080/service-frontend-0.0.3-SNAPSHOT/empresa/1"));			
			assertTrue(resource.getContent().getNombre().equals("Empresa1"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	/*private String sampleBook(String authorUrl) {
        return "{\"title\":\"Clean Code\",\"authors\":[{\"rel\":\"authors\",\"href\":\"" + authorUrl + "\"}]}";
    }*/
	
	
	@Test		
	public void testSave() {
		logger.info("Test Save - ");
		
		//jdbcTemplate.execute("DELETE FROM empresa where nif='4'");
		
		try {
			final URI empresaUri = new URI(empresaUrl);
			
			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> mediaTypes = new ArrayList<MediaType>();
			mediaTypes.add(MediaType.valueOf(acceptHeaderValue));
			requestHeaders.setAccept(mediaTypes);
			requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue));
			HttpMethod put = HttpMethod.POST;
			
			String bodyEmpresa="{\"nif\":\"4\",\"direccionFiscal\":\"dir4\",\"fechaInicioActividades\":\"1390851622000\",\"nombre\":\"Empresa4\"}";

			HttpEntity<String> entity = new HttpEntity<String>(bodyEmpresa, requestHeaders);
			
			ResponseEntity<String> response = restTemplate.exchange(empresaUri, put, entity, String.class);
			
			assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
		
	/*@Test
	public void testListado() {
		logger.info("Test Listado - ");
		try {
			Resource<Empresa> resource = getEmpresa(new URI("http://localhost:8080/rest-1.0.0/empresa"));
			assertTrue(resource.getLinks().size()!=0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test	
	public void testModificar() {
		logger.info("Test Modificar - ");
		empresaService.save(empresa1);
		empresa1 = empresaService.findByNif("123456789");
		empresa1.setNombre("Nombre cambiado");
		empresaService.save(empresa1);		
		empresas = empresaService.findAll();
		
		assertTrue(empresa1.getNombre().equals("Nombre cambiado"));		
	}*/
	
	@Test
	public void testDelete() {
		logger.info("Test Delete - ");
		
		//jdbcTemplate.execute("INSERT INTO empresa values('4','Empresa4','dir4',SYSDATE)");
		
		try {			
			restTemplate.delete(empresaUrl + "/4");
			//int count = jdbcTemplate.queryForInt("select count(*) from empresa where nif='4'");
			//assertTrue(count == 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	private Resource<Empresa> getEmpresa(URI uri) {
		return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Empresa>>() {
		}).getBody();

	}


}
