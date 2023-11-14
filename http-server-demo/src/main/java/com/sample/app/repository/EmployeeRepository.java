package com.sample.app.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sample.app.dto.Employee;

public class EmployeeRepository {

	private static Map<String, List<Employee>> EMPLOYEES_BY_CITY = new HashMap<>();

	public static void addEmployeeToCityMap(Employee emp) {
		String city = emp.getCity();

		if (EMPLOYEES_BY_CITY.containsKey(city)) {
			EMPLOYEES_BY_CITY.get(city).add(emp);
		} else {
			List<Employee> emps = new ArrayList<>();
			emps.add(emp);
			EMPLOYEES_BY_CITY.put(city, emps);
		}
	}

	static {
		Employee emp1 = new Employee(1, "Hari Krishna", 34, "Bangalore");
		Employee emp2 = new Employee(2, "Narasimha", 37, "Bangalore");
		Employee emp3 = new Employee(3, "Sailu", 35, "Hyderabad");
		Employee emp4 = new Employee(4, "Gopi", 39, "Hyderabad");
		Employee emp5 = new Employee(5, "Chamu", 33, "Chennai");
		Employee emp6 = new Employee(6, "Raghav", 34, "Chennai");
		Employee emp7 = new Employee(7, "Sudheer", 34, "Hyderabad");

		addEmployeeToCityMap(emp1);
		addEmployeeToCityMap(emp2);
		addEmployeeToCityMap(emp3);
		addEmployeeToCityMap(emp4);
		addEmployeeToCityMap(emp5);
		addEmployeeToCityMap(emp6);
		addEmployeeToCityMap(emp7);
	}

	public static List<Employee> emps() {
		List<Employee> emps = new ArrayList<>();

		for (Entry<String, List<Employee>> employeesByCity : EMPLOYEES_BY_CITY.entrySet()) {
			emps.addAll(employeesByCity.getValue());
		}

		return emps;
	}

	public static List<Employee> empsByCity(String city) {
		List<Employee> emps = EMPLOYEES_BY_CITY.get(city);
		if (emps == null) {
			return Collections.emptyList();
		}
		return emps;
	}

}
