package es.microforum.integrationtest;

import static org.junit.Assert.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

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
	
	@Before
	public void setUp() throws Exception {
		
		empresas=new ArrayList<Empresa>();
		
		empresa1=new Empresa();
		empresa1.setNif("123456789");
		empresa1.setNombre("Empresa 1");
		empresa1.setDireccionFiscal("Direccion 1");
		empresa1.setFechaInicioActividades(new Date(1985,01,01));
		empresa1.setVersion(1);
	}
		
	@Test		
	public void testSave() {
		empresaService.save(empresa1);
		assertTrue(empresa1.getNif()!=null);		
		empresas = empresaService.findAll();
		
		listaEmpresas(empresas);
	}
	
	@Test
	public void testBusquedaNif() {
		empresaService.save(empresa1);
		assertTrue(empresaService.findByNif("123456789")!=null);
	}
	
	@Test
	public void testListado() {
		empresaService.save(empresa1);
		empresas = empresaService.findAll();
		
		assertTrue(empresas.size()>0);
		listaEmpresas(empresas);
	}
	
	@Test	
	public void testModificar() {
		empresaService.save(empresa1);
		empresa1 = empresaService.findByNif("123456789");
		empresa1.setNombre("Nombre cambiado");
		empresaService.save(empresa1);		
		empresas = empresaService.findAll();
		
		listaEmpresas(empresas);
		assertTrue(empresa1.getNombre().equals("Nombre cambiado"));		
	}
	
	@Test
	public void testDelete() {
		empresaService.save(empresa1);
		empresa1 = empresaService.findByNif("123456789");
		
		assertTrue(empresa1.getNif()=="123456789");
		
		empresaService.delete(empresa1);
		
		assertTrue(empresaService.findByNif("123456789")==null);
		
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
