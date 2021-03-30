package board.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import board.board.dto.BoardDto;
import board.board.service.BoardService;

@RestController // 결과값을 json형식으로 변환해서 보내준다
public class RestBoardApiController {
		
	@Autowired
	private BoardService boardService;
	
	//게시판 글 목록 조회
	@RequestMapping(value = "/api/board", method = RequestMethod.GET) //api 주소 추가
	public List<BoardDto> openBoardList() throws Exception{
		return boardService.selectBoardList(); // 조회결과를 모델앤뷰 객체를 사용하지 않고 바로 반환한다. 
	}
	
	//게시판 글 등록
	@RequestMapping(value = "/api/board/write", method = RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto boardDto) throws Exception{
		boardService.insertBoard(boardDto, null);
	}
	
	//게시판 글 상세조회
	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		return boardService.selectBoardDetail(boardIdx);
	}
		
	//게시판 글 수정
	@RequestMapping(value = "api/board/{boardIdx}", method = RequestMethod.PUT)
	public String updateBoard(@RequestBody BoardDto boardDto) throws Exception{
		boardService.updateBoard(boardDto);
		return "redirect:/board";
	}
	
	//게시판 글 삭제
	@RequestMapping(value = "api/board/{boardIdx}", method = RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
}
