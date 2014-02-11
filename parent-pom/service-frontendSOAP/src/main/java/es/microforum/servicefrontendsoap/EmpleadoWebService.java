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
	public double callAumentoSueldo(double porcentaje){
		return empleadoService.callAumentoSueldo(porcentaje);
	}

}
