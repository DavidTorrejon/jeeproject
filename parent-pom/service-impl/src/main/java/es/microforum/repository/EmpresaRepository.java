package es.microforum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import es.microforum.model.Empresa;


public interface EmpresaRepository extends CrudRepository<Empresa, String> {

	public Empresa save(Empresa empresa);
	
	public void delete(Empresa empresa);
	
	Page<Empresa> findByNombre(String nombre, Pageable pageable);	
	
	Page<Empresa> findAll(Pageable pageable);
		
}
