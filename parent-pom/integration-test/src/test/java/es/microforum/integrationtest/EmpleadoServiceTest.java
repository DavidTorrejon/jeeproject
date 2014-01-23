package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-data-app-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional()
public class EmpleadoServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceTest.class);
	
	@Autowired
	EmpleadoService empleadoService;
	
	Empleado empleado1;	
	List<Empleado> empleados;
	
	Pageable pageable;
	
	@Before
	public void setUp() throws Exception {
		
		empleado1=new Empleado();
	
		empleado1.setDni("123456");
		empleado1.setNombre("juan");
		empleado1.setDireccion("Calle 1");
		empleado1.setTipoEmpleado("Tipo 1");
		empleado1.setEmpleadocol("Col 1");
		empleado1.setSalarioAnual(1000.00);
		empleado1.setValorHora(10.0);
		empleado1.setCantidadHoras(12.0);
		empleado1.setVersion(1);
		
		empleados=new ArrayList<Empleado>();	
		
		logger.info("Empleado creado - ");
	}
		
	@Test		
	public void testSave() {
		logger.info("Test Save - ");
		empleadoService.save(empleado1);
		assertTrue(empleado1.getDni()!=null);
		
		empleados = empleadoService.findAll();		
	}
	
	@Test
	public void testBusquedaDni() {
		logger.info("Test Busqueda Dni - ");
		empleadoService.save(empleado1);
		assertTrue(empleadoService.findByDni("123456")!=null);
	}
	
	@Test
	public void testListado() {
		logger.info("Test Listado - ");
		empleadoService.save(empleado1);
		empleados = empleadoService.findAll();
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
	
	@Test
	public void testPage() {
		logger.info("Test Page - ");
		empleadoService.save(empleado1);
		
		assertTrue(empleadoService.findByNombre("juan", pageable)!=null);
	}	
}
