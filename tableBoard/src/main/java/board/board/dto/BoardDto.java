package board.board.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {
	
	
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String createdId;
	private LocalDateTime createdDatetime;
	private String updatedId;
	private LocalDateTime updatedDatetime;
	
	private List<BoardFileDto> fileList;
	
	public List<BoardFileDto> getFileList() {
		return fileList;
	}
	public void setFileList(List<BoardFileDto> fileList) {
		this.fileList = fileList;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getHitCnt() {
		return hitCnt;
	}
	public void setHitCnt(int hitCnt) {
		this.hitCnt = hitCnt;
	}
	public String getCreatedId() {
		return createdId;
	}
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}
	public LocalDateTime getCreatedDatetime() {
		return createdDatetime;
	}
	public void setCreatedDatetime(LocalDateTime createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	public String getUpdatedId() {
		return updatedId;
	}
	public void setUpdatedId(String updatedId) {
		this.updatedId = updatedId;
	}
	public LocalDateTime getUpdatedDatetime() {
		return updatedDatetime;
	}
	public void setUpdatedDatetime(LocalDateTime updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}
	@Override
	public String toString() {
		return "BoardDto [boardIdx=" + boardIdx + ", title=" + title + ", contents=" + contents + ", hitCnt=" + hitCnt
				+ ", createdId=" + createdId + ", createdDatetime=" + createdDatetime + ", updatedId=" + updatedId
				+ ", updatedDatetime=" + updatedDatetime + ", fileList=" + fileList + "]";
	}
	
	
	
}
