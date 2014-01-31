package es.microforum.webfrontend;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.microforum.webform.EmpleadoGrid;
import es.microforum.webform.Message;
import es.microforum.webutil.UrlUtil;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceapi.EmpresaService;

@RequestMapping("/empleados")
@Controller
public class EmpleadoController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageSource messageSource;

	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private EmpresaService empresaService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing empleados");

		List<Empleado> empleados = empleadoService.findAll();
		uiModel.addAttribute("empleados", empleados);

		logger.info("No. of empleados: " + empleados.size());

		return "empleados/list";
	}
	
	@RequestMapping(value = "/{dni}", method = RequestMethod.GET)
	public String show(@PathVariable("dni") String dni, Model uiModel) {
		Empleado empleado = empleadoService.findByDniEmpresa(dni);
		uiModel.addAttribute("empleado", empleado);
		uiModel.addAttribute("empresas", empleado.getEmpresa().getNif());
		return "empleados/show";
	}

	//Cuando se va a guardar la edicion
	@RequestMapping(value = "/{dni}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Empleado empleado, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale
			,@RequestParam(value="file", required=false) MultipartFile file) {
		logger.info("Updating empleado");
		
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"empleado_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("empleado", empleado);
			return "empleados/list";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"empleado_save_success", new Object[] {}, locale)));
				
		// Process upload file
        if (file != null) {
			logger.info("File name: " + file.getName());
			logger.info("File size: " + file.getSize());
			logger.info("File content type: " + file.getContentType());
			byte[] fileContent = null;
			try {
				InputStream inputStream = file.getInputStream();
				if (inputStream == null) logger.info("File inputstream is null");
				fileContent = IOUtils.toByteArray(inputStream);
				empleado.setImagen(fileContent);
			} catch (IOException ex) {
				logger.error("Error saving uploaded file");
			}
			empleado.setImagen(fileContent);
        }
        
		Empresa empresa=new Empresa();
		empresa=empresaService.findByNif(empleado.getEmpresa().getNif());
		empleado.setEmpresa(empresa);
		empleadoService.save(empleado);
		return "redirect:/empleados/"
				+ UrlUtil.encodeUrlPathSegment(empleado.getDni(),
						httpServletRequest);
	}

	//Cuando se va a editar
	@RequestMapping(value = "/{dni}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("dni") String dni, Model uiModel) {
		Empleado empleado=empleadoService.findByDniEmpresa(dni);		

		List<Empresa> empresas = empresaService.findAll();
					
		Empresa[]aux=empresas.toArray(new Empresa[empresas.size()]);
		
		Map<String,String> sel=new HashMap<String,String>();
		sel.put(empleado.getEmpresa().getNif(), empleado.getEmpresa().getNombre());
		
		uiModel.addAttribute("empleado", empleado);
		uiModel.addAttribute("empresas",aux);	
		uiModel.addAttribute("seleccionado",sel);
		
		return "empleados/update";
	}

	@PreAuthorize("hasRole('supervisor')")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Empleado empleado, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale
			,@RequestParam(value="file", required=false) MultipartFile imagen) {
		logger.info("Creating empleado");
			
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"empleado_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("empleado", empleado);
			return "empleados/list";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"empleado_save_success", new Object[] {}, locale)));

		logger.info("Empleado dni: " + empleado.getDni());

		// Process upload file
        if (imagen != null) {
			logger.info("File name: " + imagen.getName());
			logger.info("File size: " + imagen.getSize());
			logger.info("File content type: " + imagen.getContentType());
			byte[] fileContent = null;
			try {
				InputStream inputStream = imagen.getInputStream();
				if (inputStream == null) logger.info("File inputstream is null");
				fileContent = IOUtils.toByteArray(inputStream);
				empleado.setImagen(fileContent);
			} catch (IOException ex) {
				logger.error("Error saving uploaded file");
			}
			empleado.setImagen(fileContent);
        }
        
		Empresa empresa=new Empresa();
		empresa=empresaService.findByNif(empleado.getEmpresa().getNif());
		empleado.setEmpresa(empresa);
		empleadoService.save(empleado);
		return "redirect:/empleados/"
				+ UrlUtil.encodeUrlPathSegment(empleado.getDni(),
						httpServletRequest);
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		Empleado empleado = new Empleado();

		List<Empresa> empresas = empresaService.findAll();
					
		Empresa[]aux=empresas.toArray(new Empresa[empresas.size()]);
		
		uiModel.addAttribute("empleado", empleado);
		uiModel.addAttribute("empresas", aux);
				
		return "empleados/create";
	}

	/**
	 * Support data for front-end grid
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public EmpleadoGrid listGrid(@RequestParam("name") String nombre,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		logger.info("Listing empleados for grid with page: {}, rows: {}", page,
				rows);
		logger.info("Listing empleados for grid with sort: {}, order: {}",
				sortBy, order);

		PageRequest pageRequest = null;

		pageRequest = new PageRequest(page - 1, rows);

		EmpleadoGrid empleadoGrid = new EmpleadoGrid();
		Page<Empleado> empleados;
		if (nombre == "" || nombre.equals("undefined")) {
			empleados = empleadoService.findAll(pageRequest);
		} else {
			empleados = empleadoService.findByNombre(nombre, pageRequest);
		}

		empleadoGrid.setCurrentPage(empleados.getNumber() + 1);
		empleadoGrid.setTotalPages(empleados.getTotalPages());
		empleadoGrid.setTotalRecords(empleados.getTotalElements());
		empleadoGrid.setEmpleadoData(empleados.getContent());

		return empleadoGrid;
	}
	
	@RequestMapping(value = "/photo/{dni}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] downloadPhoto(@PathVariable("dni") String dni) {
		
		Empleado empleado = empleadoService.findByDniEmpresa(dni);
        
        if (empleado.getImagen() != null) {
    		logger.info("Downloading photo for id: {} with size: {}", empleado.getDni(), empleado.getImagen().length);
        }
        
		return empleado.getImagen();
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{dni}", params = "borrar", method = RequestMethod.GET)
	public String borrar(@PathVariable("dni") String dni,
			Model uiModel, Locale locale) {
		
		logger.info("Borrando empleado");
		uiModel.addAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"empleado_delete_success", new Object[] {}, locale)));	
		
		Empleado empleado = empleadoService.findByDni(dni);
		empleadoService.delete(empleado);
		
		logger.info("Empleado borrado");
		
		return "empleados/delete";
	}
}
