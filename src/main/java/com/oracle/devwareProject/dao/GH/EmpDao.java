package com.oracle.devwareProject.dao.GH;

import java.util.List;

import com.oracle.devwareProject.domain.Emp;
import com.oracle.devwareProject.domain.EmpForSearch;
import com.oracle.devwareProject.domain.Page;

public interface EmpDao {
	public List<EmpForSearch> getAllUserInfo(Page pg);

	public List<EmpForSearch> getAllUserInfo();

	public int updateEmp(Emp emp);
}
