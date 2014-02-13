package com.microforum.ws.jaxws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;

@ContextConfiguration(locations = {"classpath:spring-data-app-context.xml"})
@WebService
public class EmpleadoWebService {
	@Autowired
	private EmpleadoService empleadoService;

	@WebMethod(exclude=true)
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}
	
	@WebMethod(operationName="callaumentosueldo")
	public List<Empleado> callAumentoSueldo(double porcentaje){
		Double resultado=0.0;
		List<Empleado> empleados=empleadoService.findAll();
		Empleado mod=new Empleado();
		List<Empleado>listEmpleado=new ArrayList<Empleado>();
		
		for(Empleado e:empleados){
			empleadoService.save(e);
			
			mod=empleadoService.findByDni(e.getDni());
			
			resultado=((e.getSalarioAnual()*porcentaje)/100)+e.getSalarioAnual();
			
			mod.setSalarioAnual(resultado);
			
			empleadoService.save(mod);
			
			listEmpleado.add(mod);
		}		
		return listEmpleado;
	}

}
