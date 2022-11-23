package com.oracle.devwareProject.domain;

import lombok.Data;

@Data
public class EmpForSearch 
{
	private int dept_num;
	private String dept_name;
	
	private int auth_num;
	private String auth_name;
	
	private int position_num;
	private String position_name;
	
	private int status_num;
	private String status_name;
}	
