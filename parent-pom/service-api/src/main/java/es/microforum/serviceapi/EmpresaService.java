package es.microforum.serviceapi;

import java.util.List;
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
}
