package com.oracle.devwareProject.dao;

import com.oracle.devwareProject.domain.Emp;
import com.oracle.devwareProject.domain.EmpList;

public interface EmpDao 
{

	int empSave(Emp emp);
	
	EmpList getEmpData(int emp_num);

	int checkEmpId(String emp_id);

	int checkSignEmp(int emp_num);

	Emp login(String emp_id);

	Emp getInfo(int emp_num);

	int changePw(String emp_passwd);

}
