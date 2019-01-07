package jpabook.start.relationship.identifying.onetooen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.relationship.identifying.onetooen.entity.Board;
import jpabook.start.relationship.identifying.onetooen.entity.BoardDetail;

public class Identifying_OneToOneMain {

	/*
	 * 일대일 식별 관계는 자식 테이블의 기본 키 값으로 부모 테이블의 기본 키 값만 사용한다.
	 * 그래서 부모 테이블의 기본 키가 복합 키가 아니면 자식 테이블의 기본 키는 복합 키로 
	 * 구성하지 않아도 된다.
	 */
	public static void main(String[] args) {

		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 일대일 식별 관계 매핑 저장 ===============");
				tx.begin();
				
				Board board = new Board("board Title");
				BoardDetail boardDetail = new BoardDetail("board Content");				
				boardDetail.setBoard(board);
				
				em.persist(board);
				em.persist(boardDetail);
				
				tx.commit();
				System.out.println("==========================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {				
				System.out.println("=============== 일대일 식별 관계 매핑 조회 ===============");
				List<BoardDetail> boardDetails = Optional.ofNullable(em.createQuery("select b from CH07_IDENTIFYING_BOARDDETAIL b where b.board.id = :id", BoardDetail.class)
					.setParameter("id", 1L)
					.getResultList() )
				.filter(b -> b.size() > 0)
				.orElseGet(ArrayList::new);
				
				boardDetails.stream().forEach(b -> {
					Board board = Optional.ofNullable(b.getBoard()).orElseGet(Board::new);
					
					System.out.println(String.format("board Id : %s, board title : %s, boardDetail id : %s, boardDetail content : %s"
						, Optional.ofNullable(board.getId()).map(String::valueOf).orElse("-1")
						, Optional.ofNullable(board.getTitle()).orElse("")
						, Optional.ofNullable(b.getId()).map(String::valueOf).orElse("-1")
						, Optional.ofNullable(b.getContent()).orElse("") ));
				});
				System.out.println("==========================================================");
			})
			.start();

	}

}
