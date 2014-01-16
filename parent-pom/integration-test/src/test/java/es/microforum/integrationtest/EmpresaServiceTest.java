package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
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
	List<Empresa> empresas;
	Empleado e;
	Set<Empleado> empleados;
	
	@Before
	public void setUp() throws Exception {
		
		ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:spring-data-app-context.xml");
		ctx.refresh();
		
		empresaService = ctx.getBean("springJpaEmpresaService",
				EmpresaService.class);
		
		empresas=new ArrayList<Empresa>();
		
		e=new Empleado();
		e.setDni("123456");
		e.setNombre("juan");
		e.setDireccion("Calle 1");
		e.setTipoEmpleado("Tipo 1");
		e.setEmpleadocol("Col 1");
		e.setSalarioAnual(1000.00);
		
		empleados=new HashSet<Empleado>();
		
		empleados.add(e);
		
		empresa1=new Empresa("123456","Empresa 1","Direccion 1",new Date(12,12,2012),empleados);
	}

	/*@Test
	public void testAll(){
		testSave();
		testBusquedaNif();
		testListado();
		testModificar();
		testDelete();
	}*/
		
	@Test	
	public void testSave() {
		empresaService.save(empresa1);
		assertTrue(empresa1.getNif()!=null);
		empresas = empresaService.findAll();
		listaEmpresas(empresas);
	}
	
	@Test
	public void testBusquedaNif() {
		assertTrue(empresaService.findByNif("123456")!=null);
	}
	
	@Test
	public void testListado() {
		empresas = empresaService.findAll();
		assertTrue(empresas.size()>0);
		listaEmpresas(empresas);
	}
	
	@Test	
	public void testModificar() {
		empresa1 = empresaService.findByNif("123456");
		empresa1.setNombre("Nombre cambiado");
		empresaService.save(empresa1);
		assertTrue(empresa1.getNombre().equals("Nombre cambiado"));
		empresas = empresaService.findAll();
		listaEmpresas(empresas);
	}
	
	@Test
	public void testDelete() {
		empresa1 = empresaService.findByNif("123456");
		empresaService.delete(empresa1);
		assertTrue(empresaService.findByNif("123456")==null);
		empresas = empresaService.findAll();
		listaEmpresas(empresas);
	}	
	
	private static void listaEmpresas(List<Empresa> empresas) {
		System.out.println("");
		for (Empresa empresa : empresas) {
			System.out.println(empresa);
			System.out.println();
		}
	}
}
