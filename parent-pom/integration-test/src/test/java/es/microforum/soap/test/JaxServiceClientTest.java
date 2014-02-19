package es.microforum.soap.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.microforum.ws.jaxws.EmpleadoWebService;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;

public class JaxServiceClientTest {
	
	EmpleadoService empleadoService;
	
	List<Empleado> empleadosOld,empleadosNew;
	
	private EmpleadoWebService empleadoWebService;


	@Before
	public void setUp() throws Exception {
			
		empleadosOld=new ArrayList<Empleado>();	
		empleadoWebService=new EmpleadoWebService();
		
		try {		
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-data-app-context.xml");
			empleadoService = context.getBean("springJpaEmpleadoService", EmpleadoService.class);

		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
	}

	@Test
	public void testCallAumentoSueldo() {
			
		//Sueldo 23.0

		Empleado viejo=new Empleado();
			
		empleadosOld = empleadoService.findAll();
		
		for(Empleado e:empleadosOld){
			empleadoService.save(e);
			
			viejo=empleadoService.findByDni(e.getDni());
		}	
		
		try {
			empleadoWebService.setEmpleadoService(empleadoService);
			empleadosNew = empleadoWebService.callAumentoSueldo(20.0);
			assertTrue(empleadosNew.size()!=0);
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		}		
			
		for(Empleado e:empleadosNew){
			if(e.getDni().equals(viejo.getDni())){
				assertTrue(viejo.getSalarioAnual()*20.0/100+viejo.getSalarioAnual()==e.getSalarioAnual());	
			}
		}		
			
				
	}
}
