package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.service.BoardService;
import board.common.FileUtils;

@Controller
public class RestBoardController {
	
	@Autowired
	private BoardService boardService;
	
	/**
	 * 글 목록 조회
	 * @param 
	 * @return List<BoardDto>
	 * @throws Exception
	 */
	@RequestMapping(value="/board", method=RequestMethod.GET ) //restful 에서는 주소와 요청방법 모두 필요하다.
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		return mv;
	}
	
	/**
	 * 글쓰기 화면 보여주기
	 * @param 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/write", method=RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		
		return "/board/restBoardWrite";
	}
	
	/**
	 * 글 등록하기
	 * @param 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		boardService.insertBoard(boardDto, multipartHttpServletRequest);
		return "redirect:/board";
	}
	
	/**
	 * 글 상세화면 조회하기
	 * @param 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.GET )
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", boardDto);
		return mv;
	}
	
	/**
	 * 글 수정하기
	 * @param 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.PUT)
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board";
	}
	
	/**
	 * 글 삭제하기
	 * @param 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
	
	/**
	 * 파일정보 조회하기
	 * @param 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/file", method = RequestMethod.GET)
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		BoardFileDto boardFile = boardService.selectFileInfomation(idx, boardIdx);
		if (ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			byte [] files = org.apache.commons.io.FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		}
		}
	
	
	
	
}
