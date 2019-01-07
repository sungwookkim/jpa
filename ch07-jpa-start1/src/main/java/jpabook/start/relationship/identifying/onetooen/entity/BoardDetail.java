package jpabook.start.relationship.identifying.onetooen.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity(name = "CH07_IDENTIFYING_BOARDDETAIL")
public class BoardDetail {

	@Id
	private long boardId;
	
	/*
	 * 식별자가 단순히 컬럼 하나면 @MapsId를 사용하고 속성 값은 비워두면 된다.
	 * 이때 @MapsId는 @Id를 사용해서 식별자로 지정한 BoardDetail.boardId와 매핑된다.
	 */
	@MapsId
	@OneToOne
	@JoinColumn(name = "BOARD_ID")
	private Board board;
	
	private String content;
	
	public BoardDetail() { }
	
	public BoardDetail(Board board, String content) {
		this.board = board;
		this.content = content;
	}
	
	public BoardDetail(String content) {
		this.content = content;
	}

	public long getId() { return boardId; }
	
	public Board getBoard() { return board; }
	public void setBoard(Board board) { 
		this.board = board;

		if(board.getBoardDetail() != this) {
			board.setBoardDetail(this);	
		}
	}

	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	
	
}
