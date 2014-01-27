package es.microforum.serviceapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.microforum.model.Empresa;

public interface EmpresaService {

	// Lista de empresas
	public List<Empresa> findAll();

	// Empresa por NIF
	public Empresa findByNif(String nif);

	// Insert o update a empresa
	public Empresa save(Empresa empresa);

	// Borra una empresa
	public void delete(Empresa empresa);

	// Busqueda por nombre paginado
	Page<Empresa> findByNombre(String nombre, Pageable pageable);

	// Busqueda de todos los empleados paginado
	Page<Empresa> findAll(Pageable pageable);
}
