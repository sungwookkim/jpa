package jpabook.start.secondaryTable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity(name = "CH07_SECONDARYTABLE")
@Table(name = "CH07_SECONDARYTABLE_BOARD")
/*
 * @SecondaryTable을 사용해서 CH07_SECONDARYTABLE_BOARDDETAIL 테이블을 추가로 매핑했다.
 */
@SecondaryTable(
	// 매핑할 다른 테이블의 이름.
	name = "CH07_SECONDARYTABLE_BOARDDETAIL"
	// 매핑할 다른 테이블의 기본 키 컬럼 속성.
	, pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_DETAIL_ID")
)
public class Board {

	@Id
	@GeneratedValue
	@Column(name = "BOARD_ID")
	private long id;
	
	private String title;
	
	// CH07_SECONDARYTABLE_BOARDDETAIL 테이블의 컬럼에 매핑.
	@Column(table = "CH07_SECONDARYTABLE_BOARDDETAIL")
	private String content;
	
	public Board() { }
	
	public Board(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public long getId() { return id; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
}
