package com.jsf.basics.bean;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


import com.jsf.basics.db.dao.EmployeeDAO;
import com.jsf.basics.db.dao.model.Employee;
import com.jsf.basics.db.service.EmployeeService;

@ManagedBean(name="employeeBean", eager = true)
@ApplicationScoped
public class EmployeeBean {

	private Long employeeID = 0L;
	
	private Set<Employee> employees;
	
	private Set<Employee> gridEmployees;
	
	private Employee selectedEmployee;
	
	
	private EmployeeService employeeService;
	
	
	public EmployeeBean() {
		employeeService = new EmployeeService(new EmployeeDAO());
	}
	
	@PostConstruct
	public void init() {
		
		if(employees == null) {
			employees = employeeService.findAll();
			setGridEmployees(employees);
		}
	}
	
	public void search(Long employeeId) {
		
		String message = "Employees not found!";
		Severity severityMessage = FacesMessage.SEVERITY_WARN;
		
		if(employeeId != null && employeeId > 0) {
			
			Employee employee = employeeService.findById(employeeId);
			if(employee != null) {
				Set<Employee> employeeSet = new HashSet<Employee>();
				employeeSet.add(employee);
				setGridEmployees(employeeSet);
				message = "Employees found!";
				severityMessage = FacesMessage.SEVERITY_INFO;
			}
		}
		else {
			employees = employeeService.findAll();
			setGridEmployees(employees);
			
			if(employees.size() > 0) {
				message = "Employees found!";
				severityMessage = FacesMessage.SEVERITY_INFO;
			}
		}
		
		FacesMessage facesMessage = new FacesMessage(severityMessage, message, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void selectEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}
	

	
	
	public Long getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public Set<Employee> getGridEmployees() {
		return gridEmployees;
	}

	public void setGridEmployees(Set<Employee> gridEmployees) {
		this.gridEmployees = gridEmployees;
	}
}
