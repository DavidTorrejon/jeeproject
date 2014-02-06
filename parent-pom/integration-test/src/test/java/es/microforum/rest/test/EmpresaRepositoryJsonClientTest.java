package es.microforum.rest.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import es.microforum.model.Empresa;

public class EmpresaRepositoryJsonClientTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EmpresaRepositoryJsonClientTest.class);
	
	private final String empresaUrl = "http://localhost:8080/rest-1.0.0/empresa";

	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void getTestNombre() {
		//Empresa con nif:11 y nombre Empresa1
		try {
			Resource<Empresa> resource = getEmpresa(new URI("http://localhost:8080/rest-1.0.0/empresa/1"));
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
		
		try {
			final URI empresaUri = new URI(empresaUrl + "/4");
			String bodyEmpresa="{\"nif\":\"4\",\"direccionFiscal\":\"4\",\"fechaInicioActividades\":\"1390851622000\",\"nombre\":\"Empresa4\"}";

			restTemplate.put(empresaUri,bodyEmpresa);
									
			Resource<Empresa> resource = getEmpresa(empresaUri);
			assertTrue(resource.getContent().getNombre().equals("Empresa4"));
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
	}
	
	@Test
	public void testDelete() {
		logger.info("Test Delete - ");
		empresaService.save(empresa1);
		empresa1 = empresaService.findByNif("123456789");
		
		assertTrue(empresa1.getNif()=="123456789");
		
		empresaService.delete(empresa1);
		
		assertTrue(empresaService.findByNif("123456789")==null);
		
		empresas = empresaService.findAll();
	}*/
	
	private Resource<Empresa> getEmpresa(URI uri) {
		return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Empresa>>() {
		}).getBody();

	}


}
