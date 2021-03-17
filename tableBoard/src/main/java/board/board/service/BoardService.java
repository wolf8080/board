package board.board.service;

import java.util.List;

import board.board.dto.BoardDto;

public interface BoardService {
	
	/**
	 * 게시판 목록조회
	 * @param 
	 * @return BoardDto
	 * @throws Exception
	 */
	List<BoardDto> selectBoardList() throws Exception;
	
	/**
	 * 게시판 글 등록하기
	 * @param 
	 * @return BoardDto
	 * @throws Exception
	 */
	
	void insertBoard(BoardDto board) throws Exception;
		
	/**
	 * 게시판 글 상세조회
	 * @param 
	 * @return BoardDto
	 * @throws Exception
	 */
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	
	/**
	 * 게시판 글 수정하기
	 * @param 
	 * @return BoardDto
	 * @throws Exception
	 */
	void updateBoard(BoardDto board) throws Exception;
	
	/**
	 * 게시판 글 삭제하기
	 * @param 
	 * @return BoardDto
	 * @throws Exception
	 */
	void deleteBoard(int boardIdx) throws Exception;
}
