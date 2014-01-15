package es.microforum.repository;

import org.springframework.data.repository.CrudRepository;
import es.microforum.model.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, String> {

	public Empleado save(Empleado empleado);
	
	public void delete(Empleado empleado);
		
}
