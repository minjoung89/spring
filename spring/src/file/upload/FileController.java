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
			File copy=new File("E://save//"+filename); // �̸��� ������ �� ���� ����
			try {
				file.transferTo(copy); // copy���Ͽ� ���� �������� ����
			} catch (Exception e) {	e.printStackTrace();}
		}
		multi.setAttribute("fileNameList", fileNameList);
		return "/0921/upload.jsp";
	}
	
	@RequestMapping("/file/download.nhn")
	public ModelAndView download(String fileName){
		File down=new File("E://save//"+fileName);
		ModelAndView mv=new ModelAndView("download","downloadFile",down);  
									 // (��Ʈ�ѷ��� Bean�� id, �Ķ�����̸�(DownloadView.java���� ������ �̸�),�ٿ�ε��� ��ü)
				/* 
					ModelAndView : Action���� View�� ���� ���� ���� 
					�Ϲ������δ� �̵��� jsp ������ �־ ��� 
					ModealAndView mv=new ModelAndView("/0921/form.jsp")
				    mv.addObject("id",id)  ==  request.setAttribute("id",id);
					return mv;
     			*/
		return mv;
	}
}
