package file.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {
	
	@RequestMapping("/file/form.nhn")
	public String form(){
		return "/0921/form.jsp";
	}
	@RequestMapping("/file/upload.nhn")
	public String fileUpload(MultipartHttpServletRequest multi, String name){
		List<MultipartFile> fileList=multi.getFiles("save");
		ArrayList<String> fileNameList=new ArrayList<String>();
		for(MultipartFile file : fileList){
			String filename=file.getOriginalFilename();
			fileNameList.add(filename);
			File copy=new File("E://save//"+filename); // 이름이 동일한 빈 파일 생성
			try {
				file.transferTo(copy); // copy파일에 기존 파일정보 복사
			} catch (Exception e) {	e.printStackTrace();}
		}
		multi.setAttribute("fileNameList", fileNameList);
		return "/0921/upload.jsp";
	}
	
	@RequestMapping("/file/download.nhn")
	public ModelAndView download(String fileName){
		File down=new File("E://save//"+fileName);
		ModelAndView mv=new ModelAndView("download","downloadFile",down);  
									 // (컨트롤러의 Bean의 id, 파라미터이름(DownloadView.java에서 지정된 이름),다운로드할 객체)
				/* 
					ModelAndView : Action에서 View로 가는 정보 관리 
					일반적으로는 이동할 jsp 페이지 넣어서 사용 
					ModealAndView mv=new ModelAndView("/0921/form.jsp")
				    mv.addObject("id",id)  ==  request.setAttribute("id",id);
					return mv;
     			*/
		return mv;
	}
}
