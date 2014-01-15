package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empresa;
import es.microforum.repository.EmpresaRepository;
import es.microforum.serviceapi.EmpresaService;

@Service("springJpaEmpresaService")
@Repository
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

	//Se inyecta cuando se carga el bean
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Transactional(readOnly=true)
	public List<Empresa> findAll() {
		return Lists.newArrayList(empresaRepository.findAll());
	}	

	public Empresa findByNif(String nif) {
		return empresaRepository.findOne(nif);
	}

	public Empresa save(Empresa empresa) {
		return empresaRepository.save(empresa);
	}

	public void delete(Empresa empresa) {
		empresaRepository.delete(empresa);
	}
}
