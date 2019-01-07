package jpabook.start.relationship.identifying.onetooen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_IDENTIFYING_BOARD")
@SequenceGenerator(
	name = "CH07_IDENTIFYING_BOARD_SEQUENCE"
	, sequenceName = "CH07_IDENTIFYING_BOARD_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_IDENTIFYING_BOARD_SEQ")
	@Column(name = "BOARD_ID")
	private long id;

	@OneToOne(mappedBy = "board")	
	BoardDetail boardDetail;
	
	private String title;
	
	public Board() { }
	
	public Board(String title) {
		this.title = title;
	}
	
	public Board(BoardDetail boardDetail, String title) {
		this.boardDetail = boardDetail;
		this.title = title;
	}

	public long getId() { return id; }

	public BoardDetail getBoardDetail() { return boardDetail; }
	public void setBoardDetail(BoardDetail boardDetail) {
		this.boardDetail = boardDetail;

		if(boardDetail.getBoard() != this) {
			boardDetail.setBoard(this);	
		}
	}

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	
}
