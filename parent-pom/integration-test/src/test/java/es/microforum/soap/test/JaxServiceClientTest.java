package es.microforum.soap.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.servicefrontendsoap.IEmpleadoWebService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-data-app-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional()
public class JaxServiceClientTest {
	
	@Autowired
	EmpleadoService empleadoService;
	
	List<Empleado> empleados;
	
	private IEmpleadoWebService empleadoWebService;


	@Before
	public void setUp() throws Exception {
		
		empleados=new ArrayList<Empleado>();	
				
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationClientContext.xml");
			empleadoWebService = (IEmpleadoWebService) context.getBean("jaxEmpleadoService");

		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
	}

	@Test
	public void testCallAumentoSueldo() {
		
		double result=0.0;
		
		Empleado aux=new Empleado();
		
		empleados = empleadoService.findAll();
		
		try {
			result = empleadoWebService.callAumentoSueldo(20.0);
			assertTrue(result==20.0);
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
		
		for(Empleado e:empleados){
			if(e.getDni().equals("1111")){
				aux=e;
			}
			e.setSalarioAnual(e.getSalarioAnual()*result/100+e.getSalarioAnual());
		}
		
		assertTrue(aux.getSalarioAnual().equals("27.6"));
		
	}
		
}
