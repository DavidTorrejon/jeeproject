package es.microforum.servicefrontendsoap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IEmpleadoWebService {

	@WebMethod(operationName = "callaumentosueldo")
	public double callAumentoSueldo(double porcentaje);

}
