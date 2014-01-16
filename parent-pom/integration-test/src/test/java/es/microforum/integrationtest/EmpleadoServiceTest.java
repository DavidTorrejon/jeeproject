package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-data-app-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional()
public class EmpleadoServiceTest {

	@Autowired
	EmpleadoService empleadoService;
	
	Empleado empleado1;	
	List<Empleado> empleados;
	
	@Before
	public void setUp() throws Exception {
		
		empleado1=new Empleado();
	
		empleado1.setDni("123456");
		empleado1.setNombre("juan");
		empleado1.setDireccion("Calle 1");
		empleado1.setTipoEmpleado("Tipo 1");
		empleado1.setEmpleadocol("Col 1");
		empleado1.setSalarioAnual(1000.00);
		
		empleados=new ArrayList<Empleado>();								
	}
		
	@Test		
	public void testSave() {
		empleadoService.save(empleado1);
		assertTrue(empleado1.getDni()!=null);
		empleados = empleadoService.findAll();
		listaEmpleados(empleados);
	}
	
	@Test
	public void testBusquedaDni() {
		empleadoService.save(empleado1);
		assertTrue(empleadoService.findByDni("123456")!=null);
	}
	
	@Test
	public void testListado() {
		empleadoService.save(empleado1);
		empleados = empleadoService.findAll();
		assertTrue(empleados.size()>0);
		listaEmpleados(empleados);
	}
	
	@Test	
	public void testModificar() {
		empleadoService.save(empleado1);
		empleado1 = empleadoService.findByDni("123456");
		empleado1.setNombre("Nombre cambiado");
		empleadoService.save(empleado1);
		assertTrue(empleado1.getNombre().equals("Nombre cambiado"));
		empleados = empleadoService.findAll();
		listaEmpleados(empleados);
	}
	
	@Test
	public void testDelete() {
		empleadoService.save(empleado1);
		empleado1 = empleadoService.findByDni("123456");
		empleadoService.delete(empleado1);
		assertTrue(empleadoService.findByDni("123456")==null);
		empleados = empleadoService.findAll();
		listaEmpleados(empleados);
	}	
	
	private static void listaEmpleados(List<Empleado> empleados) {
		System.out.println("");
		for (Empleado e : empleados) {
			System.out.println(e);
			System.out.println();
		}
	}
}
