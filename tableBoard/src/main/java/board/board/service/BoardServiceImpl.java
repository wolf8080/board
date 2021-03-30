package board.board.service;

// import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
// import org.springframework.util.ObjectUtils;
// import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;
import board.board.mapper.BoardMapper;
import board.common.FileUtils;

@Service
@Transactional //인터페이스, 메소드에서 사용
public class BoardServiceImpl implements BoardService{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		/*
		 * if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
		 * Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		 * String name; while(iterator.hasNext()) { name = iterator.next();
		 * log.debug("file tag name : " + name); List<MultipartFile> list =
		 * multipartHttpServletRequest.getFiles(name); for(MultipartFile multipartFile :
		 * list) { log.debug("start file information"); log.debug("file name : " +
		 * multipartFile.getOriginalFilename()); log.debug("file size : " +
		 * multipartFile.getSize()); log.debug("file content type : " +
		 * multipartFile.getContentType()); log.debug("end file information.\n"); } } }
		 */
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		BoardDto boardDto = boardMapper.selectBoardDetail(boardIdx);
		boardDto.setFileList(boardMapper.selectFileList(boardIdx));
		boardMapper.selectHitCnt(boardIdx);
		return boardDto;
	}
	
	@Override
	public BoardFileDto selectFileInfomation(int idx, int boardIdx) throws Exception{
		return boardMapper.selectFileInfomation(idx, boardIdx);
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
