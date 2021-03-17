package board.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	/**
	 * 게시판 목록조회
	 * @param 
	 * @return List<BoardDto>
	 * @throws Exception
	 */
	List<BoardDto> selectBoardList() throws Exception;
	
	/**
	 * 게시판 글 등록
	 * @param BoardDto
	 * @return 
	 * @throws Exception
	 */
	void insertBoard(BoardDto board) throws Exception;
	
	/**
	 * 게시판 글 상세조회
	 * @param int boardIdx
	 * @return boardDto
	 * @throws Exception
	 */
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	
	/**
	 * 게시판 글 조회수
	 * @param int boardIdx
	 * @return 
	 * @throws Exception
	 */
	void selectHitCnt(int boardIdx) throws Exception;
	
	/**
	 * 게시판 글 수정
	 * @param BoardDto
	 * @return 
	 * @throws Exception
	 */
	void updateBoard(BoardDto board) throws Exception;
	
	/**
	 * 게시판 글 삭제
	 * @param int boardIdx
	 * @return 
	 * @throws Exception
	 */
	void deleteBoard(int boardIdx) throws Exception;
}
