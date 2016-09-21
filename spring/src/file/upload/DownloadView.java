package file.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView{  
	public DownloadView(){
		setContentType("application/download;charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {  // 다운로드 실행이 이 클래스 실행됨
		File file = (File)model.get("downloadFile");
		response.setCharacterEncoding(getContentType());
		response.setContentLength((int)file.length());
		
		String fileName = java.net.URLEncoder.encode(file.getName(),"UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";"); // 다운로드 창을 띄움
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		
		//다운로드할 파일을 읽어서 사용자에게 넘김
		OutputStream out = response.getOutputStream();  
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file); // FileIntputStream에서 file 읽어들임
			FileCopyUtils.copy(fis, out); // OutputStream에 FileInputStream의 file을 복사
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null){try{fis.close();}catch(Exception e2){}}
		}
		out.flush();	
	}

}
