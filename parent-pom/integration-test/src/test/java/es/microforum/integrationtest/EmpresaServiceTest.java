package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-data-app-context.xml"})
public class EmpresaServiceTest {

	GenericXmlApplicationContext ctx;
	
	EmpresaService empresaService;
	
	@Before
	public void setUp() throws Exception {
		
		ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:spring-data-app-context.xml");
		ctx.refresh();
		
		empresaService = ctx.getBean("springJpaEmpresaService",
				EmpresaService.class);
	}

	@Test
	public void testListado() {
		List<Empresa> empresas = empresaService.findAll();
		assertTrue(empresas.size()>0);
	}
	/*
	@Test
	public void testBusquedaNif() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSave() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testModificar() {
		fail("Not yet implemented");
	}*/

}
