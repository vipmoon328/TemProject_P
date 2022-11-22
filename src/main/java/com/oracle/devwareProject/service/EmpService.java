package com.oracle.devwareProject.service;

import org.springframework.stereotype.Service;

import com.oracle.devwareProject.dao.EmpDao;
import com.oracle.devwareProject.domain.Emp;
import com.oracle.devwareProject.domain.EmpList;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpService {
	private final EmpDao ep;
	
	public int empSave(Emp emp) 
	{
		System.out.println("EmpService empSave Start");
		int result = ep.empSave(emp);
		return result;
	}
	
	public EmpList getEmpData(int emp_num) {
		System.out.println("EmpService getEmpData Start");
		EmpList empList = ep.getEmpData(emp_num);
		return empList;
	}

	public int checkEmpId(String emp_id) {
		System.out.println("EmpService checkEmpId Start");
		int result = ep.checkEmpId(emp_id);
		return result;
	}

	public int checkSignEmp(int emp_num) {
		System.out.println("EmpService checkSignEmp Start");
		int result = ep.checkSignEmp(emp_num);
		return result;
	}

	public Emp login(String emp_id) {
		System.out.println("EmpService login Start");
		Emp emp = new Emp();
		
		try {
			emp = ep.login(emp_id);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return emp;
	}

	public Emp getInfo(int emp_num) {
		System.out.println("EmpService getInfo Start");
		Emp emp = new Emp();
		
		try {
			emp = ep.getInfo(emp_num);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return emp;
	}

	public int changePw(String emp_passwd) {
		System.out.println("EmpService changePw Start");
		int result = 0;
		try {
			result = ep.changePw(emp_passwd);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

}
