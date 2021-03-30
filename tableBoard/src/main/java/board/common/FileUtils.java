package board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardFileDto;

@Component // FileUtils 클래스를 스프링의 빈으로 등록
public class FileUtils {
	
	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		// 파일 업로드될 폴더 생성
		List<BoardFileDto> fileList = new ArrayList<>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd"); // 폴더이름
		ZonedDateTime current = ZonedDateTime.now(); // 오늘날짜 확인
		String path = "images/" + current.format(format); //경로
		File file = new File(path);
		if (file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		String newFileName, originalFileExtension, contentType;
		// 파일 형식 및 이미지의 확장자를 지정
		while(iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for(MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}else {
						if(contentType.contains("image/jpg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")){
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					// 서버에 저장될 파일이름 생성
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					// 데이터베이스에 저장할 파일정보를 앞에서 만든 boardDto에 저장
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					// 업로드된 파일을 새로운 이름으로 바꾸어 저장된 경로에 저장
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
					
				}
			}
		}
		return fileList;
	}
}
