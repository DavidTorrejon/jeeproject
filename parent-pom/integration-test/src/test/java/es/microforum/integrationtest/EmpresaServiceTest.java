package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;




//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-data-app-context.xml"})
public class EmpresaServiceTest {

	GenericXmlApplicationContext ctx;
	
	EmpresaService empresaService;
	
	Empresa empresa1;
	Empresa empresa2;
	Empleado e;
	Set<Empleado> empleados;
	
	@Before
	public void setUp() throws Exception {
		
		ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:spring-data-app-context.xml");
		ctx.refresh();
		
		empresaService = ctx.getBean("springJpaEmpresaService",
				EmpresaService.class);
		
		e=new Empleado();
		e.setDni("123456");
		e.setNombre("juan");
		e.setDireccion("Calle 1");
		e.setTipoEmpleado("Tipo 1");
		e.setEmpleadocol("Col 1");
		e.setSalarioAnual(1000.00);
		
		//Inicializarlo
		//empleados=new Empleado();
		
		empleados.add(e);
		
		empresa1=new Empresa("123456","Empresa 1","Direccion 1",new Date(12,12,2012),empleados);
	}

	
	/*
	@Test
	public void testBusquedaNif() {
		fail("Not yet implemented");
	}*/
	
	@Test
	public void testSave() {
		empresaService.save(empresa1);
		assertTrue(empresa1.getNif()!=null);
	}
	
	@Test
	public void testListado() {
		List<Empresa> empresas = empresaService.findAll();
		assertTrue(empresas.size()>0);
	}
	
	/*@Test
	public void testDelete() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testModificar() {
		fail("Not yet implemented");
	}*/

}
