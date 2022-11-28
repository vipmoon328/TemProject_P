package com.oracle.devwareProject.controller.GH;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.devwareProject.domain.Authority;
import com.oracle.devwareProject.domain.Dept;
import com.oracle.devwareProject.domain.Emp;
import com.oracle.devwareProject.domain.EmpForSearch;
import com.oracle.devwareProject.domain.EmpList;
import com.oracle.devwareProject.domain.Page;
import com.oracle.devwareProject.domain.Position;
import com.oracle.devwareProject.domain.Status;
import com.oracle.devwareProject.service.GH.DeptService;
import com.oracle.devwareProject.service.GH.EmpService;
import com.oracle.devwareProject.service.GH.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmpController 
{
	private final EmpService empService;
	private final DeptService deptService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@GetMapping("/")
	public String root() {
		return "/member/index";
	}
	

	
	@GetMapping("/auth_finder")
	public String auth_finder(HttpSession session,Model model)
	{
		Emp emp = (Emp) session.getAttribute("emp");
		EmpForSearch empForSearch = (EmpForSearch) session.getAttribute("empForSearch");
		System.out.println(emp.getEmp_name()+"님은 "+empForSearch.getAuth_name()+" 입니다");
		
		if(empForSearch.getAuth_num() == 0)
		{
			model.addAttribute("emp",emp);
			model.addAttribute("empForSearch",empForSearch);
			return "/member/admin/adminMain";
		}
		
		else
		{	
			model.addAttribute("emp",emp);
			model.addAttribute("empForSearch",empForSearch);
			return "/member/user/userMain";
		}
	}
	
	@RequestMapping("/userMyPageForm")
	public String myPageForm(HttpSession session,Model model)
	{
		Emp emp = (Emp) session.getAttribute("emp");
		System.out.println("EmpService myPageForm Start");
		
		System.out.println("Name : "+emp.getEmp_name());
		
		model.addAttribute(emp);
		return "/member/user/userMyPageForm";
	}
	
	@RequestMapping("/logoutForm")
	public String logout(HttpSession session)
	{
		session.invalidate();
		return "/member/logoutForm";
	}
	
	@GetMapping("/signUpForm")
	public String signUp() {
		return "/member/signUpForm";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/member/loginForm";
	}
	
	@GetMapping("/findIdPwForm")
	public String findIdPwForm() {

		return "/member/findIdPwForm";
	}
	
	@ResponseBody
	@RequestMapping("/findId")
	public Emp findId(@RequestParam int emp_num, @RequestParam String emp_name, @RequestParam String emp_email) 
	{
		System.out.println("EmpService login Start");
		Emp emp = new Emp();
		
		System.out.println("Parameter Test 1 Num : "+emp_num);
		System.out.println("Parameter Test 2 Name : "+emp_name);
		System.out.println("Parameter Test 3 Email: "+emp_email);
		
		try {
			emp = empService.getInfo(emp_num);
			System.out.println("쿼리문 실행 후 ");
			if(emp_name.equals(emp.getEmp_name()) && emp_email.equals(emp.getEmp_email()))
			{
				System.out.println("정보가 모두 일치 되었습니다.");
				System.out.println("당신의 아이디는 " + emp.getEmp_id() + " 입니다.");
				emp.setMsg(emp.getEmp_name()+"의 아이디는 "+emp.getEmp_id()+" 입니다.");
				emp.setResult(1);
			} else { 
				System.out.println("정보가 일치하지 않습니다.");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return emp;
	}
	
	@ResponseBody
	@RequestMapping("/findPw")
	public Emp findPw(@RequestParam int emp_num, @RequestParam String emp_name, @RequestParam String emp_id, @RequestParam String emp_email) 
	{
		System.out.println("EmpService login Start");
		Emp emp = new Emp();
		
		System.out.println("Parameter Test 1 Num : "+ emp_num);
		System.out.println("Parameter Test 2 Name : "+ emp_name);
		System.out.println("Parameter Test 3 Email: "+ emp_email);
		System.out.println("Parameter Test 4 Id: "+ emp_id);
		try {
			emp = empService.getInfo(emp_num);
			
			if(emp_name.equals(emp.getEmp_name()) && emp_email.equals(emp.getEmp_email()) && emp_id.equals(emp.getEmp_id()) )
			{
				System.out.println("정보가 모두 일치 되었습니다.");
				System.out.println("당신의 아이디는 " + emp.getEmp_id() + " 입니다.");
				emp.setMsg("비밀 번호를 수정합니다. <br><h5>비밀 번호를 입력해주세요</h5></br>");
				emp.setResult(1);
			}
			else {
				System.out.println("정보가 일치하지 않습니다.");
				emp = new Emp();
				emp.setMsg("비밀번호 찾기에 실패하셨습니다.");
				emp.setResult(0);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return emp;
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public Emp login(@RequestParam String emp_id, @RequestParam String emp_passwd, HttpServletRequest request) {
		System.out.println("EmpService login Start");
		
		Emp emp = new Emp();
		
		//현재 로그인을 요청한 Emp Id를 가져오기
		try {
			emp = empService.login(emp_id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// 위 단계에서 조회한 Emp의 비밀번호와 현재 로그인을 요청한 비밀번호와 비교 
		if(encoder.matches(emp_passwd,emp.getEmp_passwd()))
		{
			HttpSession session =request.getSession();	
			System.out.println("로그인 성공");
			emp.setMsg(emp.getEmp_name()+"님 환영합니다.");
			emp.setAuth_num(emp.getAuth().getAuth_num());
			emp.setResult(1);
			
			//조회용 empForSearch에 값을 넣기
			EmpForSearch empForSearch = new EmpForSearch();
			empForSearch.setAuth_name(emp.getAuth().getAuth_name());
			empForSearch.setAuth_num(emp.getAuth().getAuth_num());
			
			empForSearch.setDept_name(emp.getDept().getDept_name());
			empForSearch.setDept_num(emp.getDept().getDept_num());
			
			empForSearch.setPosition_name(emp.getPosition().getPosition_name());
			empForSearch.setPosition_num(emp.getPosition().getPosition_num());
			
			empForSearch.setStatus_name(emp.getStatus().getStatus_name());
			empForSearch.setStatus_num(emp.getStatus().getStatus_num());
			
			session.setAttribute("empForSearch", empForSearch);
			session.setAttribute("emp", emp);
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
		return "/member/loginForm";
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
	
	@ResponseBody
	@RequestMapping("/changePw")
	public int changePw(@RequestParam String emp_passwd, @RequestParam int emp_num)
	{
		int result = 0;
		System.out.println("EmpService changePw Start");
		
		System.out.println("emp_num : "+ emp_num);
		System.out.println("emp_passwd : "+emp_passwd);
		
		Emp emp = new Emp();
		try {
			emp = empService.getInfo(emp_num);
			emp.setResult(1);
			System.out.println("암호화된 비밀번호: "+encoder.encode(emp_passwd));
			emp.setEmp_passwd(encoder.encode(emp_passwd));
			result = empService.changePw(emp.getEmp_passwd(),emp_num);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			emp.setResult(0);
		}
		
		
		return result;
	}
		
	@RequestMapping("/userlist")
	public String userlist(Model model, HttpSession session, String currentPage)
	{
		Emp emp = (Emp) session.getAttribute("emp");
		EmpForSearch empForSearch = (EmpForSearch) session.getAttribute("empForSearch");
		System.out.println("EmpService userlist [모든 유저 조회 기능] Start");
		
		//직원 조회를 위한 리스트
		List<EmpForSearch> emplist = new ArrayList<EmpForSearch>();
		
		Page pg = new Page();
		
		//전체 직원수를 체크
		int totalEmp = empService.getEmpCount();
		
		//부서 이름 조회를 위한 리스트
		List<Dept> deptlist = new ArrayList<Dept>();
		
		System.out.println("모든 사용자의 수 : "+ totalEmp);
		
		Paging page = new Paging(totalEmp, currentPage);
		pg.setStart(page.getStart());
		pg.setEnd(page.getEnd());
		
		try {
			//직원 조회 기능 수행
			emplist = empService.getAllUserInfo(pg);
			//부서 조회 기능 수행
			deptlist = deptService.getDeptInfo();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		model.addAttribute("emplist",emplist);
		model.addAttribute("emp",emp);
		model.addAttribute("page", page);
		model.addAttribute("deptlist",deptlist);
		
		return "/member/admin/userlist";
	}
	
	@RequestMapping("/userlistDeptSearch")
	public String userlistDeptSearch(@RequestParam int deptnum, Model model, HttpSession session, String currentPage)
	{
		Emp emp = (Emp) session.getAttribute("emp");
		System.out.println("EmpService userlist ['부서별 유저 조회 기능'] Start");
		
		//직원 조회를 위한 리스트
		List<EmpForSearch> emplist = new ArrayList<EmpForSearch>();
		
		Page pg = new Page();
		
		//전체 직원수를 체크
		int totalEmp = empService.getEmpCount(deptnum);
		
		//부서 이름 조회를 위한 리스트
		List<Dept> deptlist = new ArrayList<Dept>();
		
		System.out.println("모든 사용자의 수 : "+ totalEmp);
		
		Paging page = new Paging(totalEmp, currentPage);
		pg.setStart(page.getStart());
		pg.setEnd(page.getEnd());
		
		try {
			//직원 조회 기능 수행
			emplist = empService.getUserInfo(pg,deptnum);
			//부서 조회 기능 수행
			deptlist = deptService.getDeptInfo();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		model.addAttribute("emplist",emplist);
		model.addAttribute("emp",emp);
		model.addAttribute("page", page);
		model.addAttribute("deptlist",deptlist);
		
		return "/member/admin/userlist";
	}
	
	
	@PostMapping("/editInfo")
	public String upDateUser(Emp new_emp, Model model, HttpSession session)
	{
		Emp emp = (Emp) session.getAttribute("emp");
		System.out.println("EmpService editInfo Start");
		int result = 0;
		
		try {
			//이름 변경
			emp.setEmp_name(new_emp.getEmp_name());
			System.out.println("이름: "+new_emp.getEmp_name());
			
			//비밀번호 수정한 경우 암호화 한 후 다시 변경 
			if(emp.getEmp_passwd().equals(new_emp.getEmp_passwd()))
			{
				emp.setEmp_passwd(new_emp.getEmp_passwd());
			}
			
			else {
				String temp = new_emp.getEmp_passwd();
				temp = encoder.encode(temp);
				emp.setEmp_passwd(temp);
			}
			
			System.out.println("비밀번호: "+emp.getEmp_passwd());
			
			//이메일 변경
			emp.setEmp_email(new_emp.getEmp_email());
			
			System.out.println("이메일: "+new_emp.getEmp_email());
			
			//주소 변경
			emp.setEmp_address(new_emp.getEmp_address());
			
			System.out.println("주소: " + new_emp.getEmp_address());
			
			//변경 사항을 반영한 update문 
			result = empService.updateEmp(emp);
			
			if(result == 1)
			{
				System.out.println("회원 정보 변경에 성공하였습니다.");
			}
			else if(result == 2)
			{
				System.out.println("회원 정보 변경에 실패하였습니다.");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		model.addAttribute("emp",emp);
		model.addAttribute("result",result);
		
		return "/member/user/userMyPageForm";
	}
}
