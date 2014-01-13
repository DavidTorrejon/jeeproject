package es.microforum.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EmpleadoTest {

	Empleado empleado;
	
	@Before
	public void setUp() throws Exception {
		empleado=new Empleado("123456");
	}

	@Test
	public void testEquals() {
		assertTrue(empleado!=null);
		assertTrue(empleado.getDni().equals("123456"));
	}

}
