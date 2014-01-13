package es.microforum.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EmpresaTest {

	Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa=new Empresa("123456");
	}

	@Test
	public void testEquals() {
		assertTrue(empresa!=null);
		assertTrue(empresa.getNif().equals("123456"));
	}

}
