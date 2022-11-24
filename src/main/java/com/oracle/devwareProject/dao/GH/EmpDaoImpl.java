package com.oracle.devwareProject.dao.GH;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.devwareProject.domain.Emp;
import com.oracle.devwareProject.domain.EmpForSearch;
import com.oracle.devwareProject.domain.Page;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {
	
	private final SqlSession session;
	
	@Override
	public List<EmpForSearch> getAllUserInfo() {
		List<EmpForSearch> emplist = new ArrayList<EmpForSearch>();
		try {
			emplist = session.selectList("getAllUserInfo_All");
			System.out.println("EmpDaoImpl emplist.size: " + emplist.size());
		} catch (Exception e) {
			System.out.println("Error Occurred " + e.getMessage());
		}
		
		return emplist;
	}
	
	@Override
	public List<EmpForSearch> getAllUserInfo(Page pg) 
	{
		List<EmpForSearch> emplist = new ArrayList<EmpForSearch>();
		try {
			emplist = session.selectList("getAllUserInfo_Paging",pg);
			System.out.println("EmpDaoImpl emplist.size: " + emplist.size());
		} catch (Exception e) {
			System.out.println("Error Occurred " + e.getMessage());
		}
		
		return emplist;
	}


}
