package com.example.hibernateTest.hibernateTest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import domain.Employee;

@SpringBootApplication
public class HibernateTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateTestApplication.class, args);
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// Get employee with id = 4 from table
		Employee employee = entityManager.find(Employee.class, 4);
		System.out.println(employee.getSupervisor().getName());

		// Add new employee to table
		Employee employeeToBeAdded = new Employee();
		employeeToBeAdded.setName("John");
		employeeToBeAdded.setSupervisor(employee);
		// entityManager.getTransaction().begin();
		// entityManager.merge(employeeToBeAdded);
		// entityManager.getTransaction().commit();

		// Select all employees with supervisor_id = 1;
		// HQL Query
		String query = "from Employee where supervisor.id = :id";
		List<Employee> employees = entityManager.createQuery(query, Employee.class).setParameter("id", 1)
				.getResultList();
		employees.forEach(s -> System.out.println(s.getName()));

		entityManager.close();
		entityManagerFactory.close();
	}
}
