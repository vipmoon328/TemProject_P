package com.oracle.devwareProject.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.oracle.devwareProject.domain.Emp;
import com.oracle.devwareProject.domain.EmpList;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class EmpDaoImpl implements EmpDao {
	private final EntityManager em;
	
	public int empSave(Emp emp) 
	{
		int result = 0;
		try {
			System.out.println("EmpDaoImpl empSave Start");
			System.out.println("test getEmp_hireDate: " + emp.getEmp_hireDate());
			em.persist(emp);
			result++;
		} catch (Exception e) {
			System.out.println("EmpDaoImpl empSave Error: " + e.getMessage());
		}
		return result;
	}

	@Override
	public EmpList getEmpData(int emp_num) {
		EmpList emplist = new EmpList();
		System.out.println("EmpDaoImpl getEmpData Start");
		
		try {
			emplist = em.find(EmpList.class, emp_num);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl getEmpData 에러 발생");
		}
		return emplist;
	}

	@Override
	public int checkEmpId(String emp_id) {
		System.out.println("EmpDaoImpl getEmpData Start");
		System.out.println("EmpDaoImpl emp_id : " + emp_id);
		
		int result = 0;
		List<Emp> emplist = new ArrayList<Emp>();
		
		try {
			emplist = em.createQuery("SELECT e FROM Emp e where e.emp_id = :emp_id", Emp.class)
									.setParameter("emp_id", emp_id)
									.getResultList();
			result = emplist.size();
			System.out.println("checkEmpId 쿼리문 결과 값: " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	@Override
	public int checkSignEmp(int emp_num) {
		// TODO Auto-generated method stub
		System.out.println("EmpDaoImpl checkSignEmp Start");
		System.out.println("EmpDaoImpl emp_num : " + emp_num);
		
		int result = 0;
		List<Emp> emplist = new ArrayList<Emp>();
		
		try {
			emplist = em.createQuery("SELECT e FROM Emp e where e.emp_num = :emp_num", Emp.class)
									.setParameter("emp_num", emp_num)
									.getResultList();
			result = emplist.size();
			System.out.println("checkSignEmp 쿼리문 결과 값: " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	@Override
	public Emp login(String emp_id) {
		System.out.println("EmpDaoImpl login Start");
		System.out.println("EmpDaoImpl emp_id : " + emp_id);
		
		Emp emp = new Emp();
		
		try {
			emp = em.createQuery("SELECT e FROM Emp e where e.emp_id = :emp_id", Emp.class)
								.setParameter("emp_id", emp_id)
								.getSingleResult();
			System.out.println("login 쿼리문 결과 값: " + emp.getEmp_id());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return emp;
	}


}
