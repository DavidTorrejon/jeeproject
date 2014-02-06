package es.microforum.rest.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-data-app-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional()
public class EmpleadoRepositoryJsonClientTest {

	private static final Logger logger = LoggerFactory.getLogger(EmpleadoRepositoryJsonClientTest.class);
	
	RestTemplate restTemplate = new RestTemplate();
		
	/*@Test		
	public void testSave() {
		logger.info("Test Save - ");			
		
		try {
			Resource<Empleado> resource = getEmpleado(new URI("http://localhost:8080/rest-1.0.0/empleado"));
			Empleado prueba=resource.getContent().setDni("dniPrueba");
			Resource<Empleado> resource = getEmpleado(new URI("http://localhost:8080/rest-1.0.0/empleado/dniPrueba"));
			assertTrue(resource.getContent().getNombre().equals("juan"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
			
	@Test
	public void testListado() {
		logger.info("Test Listado - ");
		empleadoService.save(empleado1);
		
		try {
			Resource<Empleado> resource = getEmpleado(new URI("http://localhost:8080/rest-1.0.0/empleado/dniPrueba"));
			assertTrue(resource.getContent().getNombre().equals("juan"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(empleados.size()>0);
	}
	
	@Test	
	public void testModificar() {
		logger.info("Test Modificar - ");
		empleadoService.save(empleado1);
		empleado1 = empleadoService.findByDni("123456");
		empleado1.setNombre("Nombre cambiado");
		empleadoService.save(empleado1);
		
		assertTrue(empleado1.getNombre().equals("Nombre cambiado"));
		
		empleados = empleadoService.findAll();
	}
	
	@Test
	public void testDelete() {
		logger.info("Test Delete - ");
		empleadoService.save(empleado1);
		empleado1 = empleadoService.findByDni("123456");
		empleadoService.delete(empleado1);
		
		assertTrue(empleadoService.findByDni("123456")==null);
		
		empleados = empleadoService.findAll();
	}	

	private Resource<Empleado> getEmpleado(URI uri) {
		return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Empleado>>() {
		}).getBody();

	}*/


}
