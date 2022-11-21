package com.oracle.devwareProject.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.devwareProject.domain.Authority;
import com.oracle.devwareProject.domain.Dept;
import com.oracle.devwareProject.domain.Emp;
import com.oracle.devwareProject.domain.EmpList;
import com.oracle.devwareProject.domain.Position;
import com.oracle.devwareProject.domain.Status;
import com.oracle.devwareProject.service.EmpService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmpController 
{
	private final EmpService empService;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@GetMapping("/")
	public String root() {
		return "/index";
	}
	
	@GetMapping("/signUpForm")
	public String signUp() {
		return "/signUpForm";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/loginForm";
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public Emp login(@RequestParam String emp_id, @RequestParam String emp_passwd) {
		System.out.println("EmpService login Start");
		Emp emp = new Emp();
		try {
			emp = empService.login(emp_id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if(encoder.matches(emp_passwd,emp.getEmp_passwd()))
		{
			System.out.println("로그인 성공");
			emp.setMsg(emp.getEmp_name()+"님 환영합니다.");
			emp.setResult(1);
		}
		else
		{
			System.out.println("로그인 실패");
			emp.setMsg("로그인에 실패하셨습니다.");
			emp.setResult(0);
		}
		
		return emp;
	}
	
	@ResponseBody
	@PostMapping("/checkSignEmp")
	public int checkSignEmp(int emp_num)
	{
		System.out.println("EmpService checkSignEmp Start");
		int result = empService.checkSignEmp(emp_num);
		String res = "";
		
		if(result > 0)
		{
			res = "이미 계정이 존재";
		}
		else {
			res = "회원  가입 가능";
		}
		
		System.out.println(res);
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("/getEmpData")
	public EmpList getEmpData(int emp_num, String emp_name)
	{
		System.out.println("EmpService getEmpData Start");
		System.out.println("Emp_num : "+emp_num);
		System.out.println("Emp_name : "+emp_name);
		
		EmpList emplist = new EmpList();
		try {
			emplist = empService.getEmpData(emp_num);
			System.out.println("emplist Emp_num : "+emplist.getEmp_num());
			System.out.println("emplist Emp_name : "+emplist.getEmp_name());
			System.out.println("emplist 권한: " + emplist.getAuth_num());

			if(emplist.getEmp_name().equals(emp_name))
			{
				System.out.println("사용자 확인 성공");
				emplist.setMsg("사원 조회에 성공했습니다.");
				emplist.setResult(1);
			}
			else {
				System.out.println("사용자 확인 실패");
				emplist.setMsg("사원 조회에 실패했습니다.");
				emplist.setResult(0);
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			emplist = new EmpList();
			System.out.println("사용자 확인 실패");
			emplist.setMsg("사원 조회에 실패했습니다.");
			emplist.setResult(0);
		}
		
		return emplist;
	}
	
	@RequestMapping(value = "/empSave" )
	public String empSave(Emp emp,@RequestParam int position_num,@RequestParam int status_num, @RequestParam int auth_num, @RequestParam int dept_num)
	{
		Position position = new Position();
		position.setPosition_num(position_num);
		emp.setPosition(position);
		
		Status status = new Status();
		status.setStatus_num(status_num);
		emp.setStatus(status);
		
		Authority authority = new Authority();
		authority.setAuth_num(auth_num);
		emp.setAuth(authority);
		
		Dept dept = new Dept();
		dept.setDept_num(dept_num);
		emp.setDept(dept);
		
		System.out.println("EmpController EnCrypt Start");
		
		
		String securePassword = encoder.encode(emp.getEmp_passwd());
		emp.setEmp_passwd(securePassword);
		
		System.out.println("암호화된 비밀번호: "+securePassword);
		 
		System.out.println("EmpController empSave Start...");
		int result = empService.empSave(emp);
		 
		if (result>0){
			System.out.println("Emp 테이블 직원 저장 완료");
		}
		else {
			System.out.println("Emp 테이블 직원 저장 실패");
		}
		return "";
	}
	
	@ResponseBody
	@PostMapping("checkEmpId")
	public int checkEmpId(String emp_id)
	{
		System.out.println("EmpService checkEmpId Start");
		int result = empService.checkEmpId(emp_id);
		String res = "";
		
		if(result > 0)
		{
			res = "중복";
		}
		else {
			res = "사용 가능";
		}
		
		System.out.println(res);
		
		return result;
	}
}