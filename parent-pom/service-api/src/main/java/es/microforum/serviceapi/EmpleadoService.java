package es.microforum.serviceapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.microforum.model.Empleado;

public interface EmpleadoService {

	// Listado de empleados
	public List<Empleado> findAll();

	// Empleado por DNI
	public Empleado findByDni(String dni);

	// Insert o update a empleado
	public Empleado save(Empleado empleado);

	// Borra un empleado
	public void delete(Empleado empleado);
	
	// Busqueda por nombre paginado
	Page<Empleado> findByNombrePageable(String nombre, Pageable pageable);
}
