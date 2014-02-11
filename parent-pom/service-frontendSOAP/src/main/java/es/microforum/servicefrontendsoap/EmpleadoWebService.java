package es.microforum.servicefrontendsoap;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;

@WebService
public class EmpleadoWebService {
	private EmpleadoService empleadoService;

	@WebMethod(exclude=true)
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}
	
	@WebMethod(operationName="callaumentosueldo")
	public List<Empleado> callAumentoSueldo(List<Empleado>empleados,double porcentaje){
		List<Empleado>listEmpleado=new ArrayList<Empleado>();
		Double resultado=0.0;
		for(Empleado e:empleados){
			resultado=((e.getSalarioAnual()*porcentaje)/100)+e.getSalarioAnual();
			e.setSalarioAnual(resultado);
			listEmpleado.add(e);
		}		
		return empleadoService.callAumentoSueldo(listEmpleado);
	}
}
