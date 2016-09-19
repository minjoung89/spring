package test.bean;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	@Autowired 		//컨트롤러에 있는 sqlMapClientTemplate 객체가 자동으로 sqlMap으로 들어오게됨
	private SqlMapClientTemplate sqlMap;	
	
	@RequestMapping("/hello.nhn")
	public String hello(){
		System.out.println("hello");
		return "/0919/hello.jsp";
	}
	
	@RequestMapping("/helloPro.nhn")
	public String helloPro(DTO dto, HttpServletRequest request){
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		
		return "/view/index.jsp";
	}
}
