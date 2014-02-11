package es.microforum.servicefrontendsoap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import es.microforum.model.Empleado;

@WebService
public interface IEmpleadoWebService {

	@WebMethod(operationName = "callaumentosueldo")
	public List<Empleado> callAumentoSueldo(double porcentaje);
}
