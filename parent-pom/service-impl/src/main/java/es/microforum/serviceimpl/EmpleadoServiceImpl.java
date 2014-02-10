package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.microforum.model.Empleado;
import es.microforum.repository.EmpleadoRepository;
import es.microforum.serviceapi.EmpleadoService;
import com.google.common.collect.Lists;

@Service("springJpaEmpleadoService")
@Repository
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

	//Se inyecta cuando se carga el bean
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Transactional(readOnly=true)
	public List<Empleado> findAll() {
		return Lists.newArrayList(empleadoRepository.findAll());
	}	

	public Empleado findByDni(String dni) {
		return empleadoRepository.findOne(dni);
	}
	
	public Empleado findByDniEmpresa(String dni) {
		Empleado empleado=empleadoRepository.findOne(dni);
		empleado.getEmpresa().getEmpleados().size();
		return empleado;
	}

	public Empleado save(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	public void delete(Empleado empleado) {
		empleadoRepository.delete(empleado);
	}
	
	@Transactional(readOnly = true)
	public Page<Empleado> findByNombre(String nombre, Pageable pageable) {
		return empleadoRepository.findByNombre(nombre, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Empleado> findAll(Pageable pageable) {
		return empleadoRepository.findAll(pageable);
	}

	@Override
	public List<Empleado> callAumentoSueldo(List<Empleado>empleados) {
		return empleados;
	}	
}
