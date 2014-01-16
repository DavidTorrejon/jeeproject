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
public class EmpresaServiceTest {

	@Autowired
	EmpresaService empresaService;
	
	Empresa empresa1;
	List<Empresa> empresas;
	Empleado e;
	Set<Empleado> empleados;
	
	@Before
	public void setUp() throws Exception {
		
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
		
	@Test		
	public void testSave() {
		empresaService.save(empresa1);
		assertTrue(empresa1.getNif()!=null);
		empresas = empresaService.findAll();
		listaEmpresas(empresas);
		listaEmpleados(empresa1);
	}
	
	@Test
	public void testBusquedaNif() {
		empresaService.save(empresa1);
		assertTrue(empresaService.findByNif("123456")!=null);
	}
	
	@Test
	public void testListado() {
		empresaService.save(empresa1);
		empresas = empresaService.findAll();
		assertTrue(empresas.size()>0);
		listaEmpresas(empresas);
		listaEmpleados(empresa1);
	}
	
	@Test	
	public void testModificar() {
		empresaService.save(empresa1);
		empresa1 = empresaService.findByNif("123456");
		empresa1.setNombre("Nombre cambiado");
		empresaService.save(empresa1);
		
		empresas = empresaService.findAll();
		listaEmpresas(empresas);
		listaEmpleados(empresa1);
		assertTrue(empresa1.getNombre().equals("Nombre cambiado"));		
	}
	
	@Test
	public void testDelete() {
		empresaService.save(empresa1);
		empresa1 = empresaService.findByNif("123456");
		assertTrue(empresa1.getNif()=="123456");
		empresaService.delete(empresa1);
		assertTrue(empresaService.findByNif("123456")==null);
		empresas = empresaService.findAll();
		listaEmpresas(empresas);
		listaEmpleados(empresa1);
	}	
	
	private static void listaEmpresas(List<Empresa> empresas) {
		System.out.println("");
		for (Empresa empresa : empresas) {
			System.out.println(empresa);
			System.out.println();
		}
	}
	
	private static void listaEmpleados(Empresa empresas) {
		Set<Empleado>e=new HashSet<Empleado>();
		e=empresas.getEmpleados();
		for (Empleado emp : e) {
			System.out.println(emp.getNombre());
			System.out.println();
		}
	}
}
