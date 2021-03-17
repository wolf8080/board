package board.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.board.dto.BoardDto;
import board.board.service.BoardServiceImpl;

@Controller
public class BoardController {
	//로거 생성
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardServiceImpl boardService;
	
	/**
	 * 게시판 목록조회
	 * @param 
	 * @return BoardDto
	 * @throws Exception
	 */
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		log.debug("openBoardList"); //debug레벨의 로그를 출력
		ModelAndView mv = new ModelAndView("/board/boardList");
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
		
		/**
		 * 게시판 글 등록화면 조회
		 * @param 
		 * @return BoardDto
		 * @throws Exception
		 */
	@RequestMapping("/board/openBoardWrite.do")
	public ModelAndView openBoardWrite() throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardWrite");
		return mv;
	}
		/**
		 * 게시판 글 등록
		 * @param BoardDto
		 * @return 
		 * @throws Exception
		 */
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board) throws Exception{
		boardService.insertBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
		/**
		 * 게시판 상세조회
		 * @param 
		 * @return BoardDto
		 * @throws Exception
		 */
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception{
			ModelAndView mv = new ModelAndView("/board/boardDetail");
			BoardDto board = boardService.selectBoardDetail(boardIdx);
			mv.addObject("board", board);
			return mv;
	}
		/**
		 * 게시판 글 수정
		 * @param 
		 * @return BoardDto
		 * @throws Exception
		 */
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
		/**
		 * 게시판 글 삭제
		 * @param 
		 * @return BoardDto
		 * @throws Exception
		 */
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do";
	}
}

