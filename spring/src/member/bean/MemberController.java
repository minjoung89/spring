package member.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	@Autowired
	SqlMapClientTemplate sqlMap;  // controller.xml로부터 설정이 끝난 ibatis 정보를 받아옴 (DI)
	
	@RequestMapping("/member/main.nhn")
	public String main(){
		return "/member/main.jsp";
	}
	
	@RequestMapping("/member/inputForm.nhn")
	public String inputForm(){
		return "/member/inputForm.jsp";
	}
	
	@RequestMapping("/member/inputPro.nhn")
	public String inputPro(LogonDataBean dto){
		sqlMap.update("insertMember", dto);
		return "redirect:/member/main.nhn"; // 리다이렉트로 이동
	}
	
	@RequestMapping("/member/loginPro.nhn")
	public String loginPro(LogonDataBean dto,HttpSession session,HttpServletRequest request){
		int check=(Integer)sqlMap.queryForObject("userCheck", dto);
		String view="";
		if(check==1){
			session.setAttribute("memId", dto.getId());
			view="redirect:/member/main.nhn";
		}else{
			request.setAttribute("check", new Integer(check));
			view="/member/loginPro.jsp";
		}
		return view;
	}
	
	@RequestMapping("/member/logout.nhn")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/member/main.nhn";
	}
	
	@RequestMapping("/member/modify.nhn")
	public String modify(){
		return "/member/modify.jsp";
	}
	
	@RequestMapping("/member/modifyForm.nhn")
	public String modifyForm(HttpSession session,HttpServletRequest request){
		String id=(String) session.getAttribute("memId");
		LogonDataBean dto=(LogonDataBean) sqlMap.queryForObject("selectMember", id);
		request.setAttribute("dto", dto);
		return "/member/modifyForm.jsp";
	}
	
	@RequestMapping("/member/modifyPro.nhn")
	public String modifyPro(LogonDataBean dto,HttpSession session){
		dto.setId((String) session.getAttribute("memId"));
		sqlMap.update("updateMember", dto);
		return "/member/modifyPro.jsp";
	}
	
	@RequestMapping("/member/deleteForm.nhn")
	public String deleteForm(){
		return "/member/deleteForm.jsp";
	}
	
	@RequestMapping("/member/deletePro.nhn")
	public String deletePro(LogonDataBean dto,HttpSession session,HttpServletRequest request){
		dto.setId((String) session.getAttribute("memId"));
		int check=(Integer)sqlMap.queryForObject("userCheck", dto);
		if(check==1){ 
			session.invalidate(); 
			sqlMap.delete("deleteMember", dto);
		}
		request.setAttribute("check", new Integer(check));
		
		return "/member/deletePro.jsp";
	}
	
	@RequestMapping("/member/confirmId.nhn")
	public String confirmId(LogonDataBean dto,HttpServletRequest request){
		int check=(Integer)sqlMap.queryForObject("idCheck", dto);
		request.setAttribute("id", dto.getId());
		request.setAttribute("check", new Integer(check));
		return "/member/confirmId.jsp";
	}
	
}
