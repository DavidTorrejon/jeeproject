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
	
	// Empleado por DNI con empresa
	public Empleado findByDniEmpresa(String dni);

	// Insert o update a empleado
	public Empleado save(Empleado empleado);

	// Borra un empleado
	public void delete(Empleado empleado);
	
	// Busqueda por nombre paginado
	Page<Empleado> findByNombre(String nombre, Pageable pageable);
	
	// Busqueda de todos los empleados paginado
	Page<Empleado> findAll(Pageable pageable);
	
	// Aumento de sueldo para los empleados
	public List<Empleado> callAumentoSueldo(double porcentaje);
}
