package board.board.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.board.dto.BoardDto;
import board.board.mapper.BoardMapper;

@Service
@Transactional //인터페이스, 메소드에서 사용
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board) throws Exception{
		boardMapper.insertBoard(board);
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		
		boardMapper.selectHitCnt(boardIdx);
		return boardMapper.selectBoardDetail(boardIdx);
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception{
		boardMapper.updateBoard(board);
	}
	
	@Override
	public void deleteBoard(int boardIdx) throws Exception{
		boardMapper.deleteBoard(boardIdx);
	}
}
