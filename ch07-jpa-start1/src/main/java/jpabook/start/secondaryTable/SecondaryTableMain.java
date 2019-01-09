package jpabook.start.secondaryTable;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.secondaryTable.entity.Board;

public class SecondaryTableMain {

	/*
	 * @SecondaryTable을 사용하면 한 엔티티에 여러 테이블을 매핑할 수 있다.
	 * 그러나 @SecondaryTable을 사용해서 두 테이블을 하나의 엔티티에 매핑하는 방법보다는
	 * 테이블당 엔티티를 각각 만들어 일대일 매핑하는 것을 권장한다.
	 * 이 방법은 항상 두 테이블을 조회하므로 최적화하기 어렵다.
	 * 반면 일대일 매핑은 원하는 부분만 조회할 수 있고 필요하면 함께 조회하면 된다. 
	 */
	public static void main(String[] args) {
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== SecondaryTable 매핑 저장 ===============");
				tx.begin();
				
				em.persist(new Board("sinnake_title", "sinnake_content"));

				tx.commit();
				System.out.println("========================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== SecondaryTable 매핑 조회 ===============");
				Board board = Optional.ofNullable(em.createQuery("select b from CH07_SECONDARYTABLE b", Board.class)
					.getResultList() )
				.filter(b -> b.size() > 0)
				.map(b -> b.get(0))
				.orElseGet(Board::new);
				
				System.out.println(String.format("board id : %s, board title : %s, board content : %s"
					, Optional.ofNullable(board.getId()).orElse(-1L)
					, Optional.ofNullable(board.getTitle()).orElse("")
					, Optional.ofNullable(board.getContent()).orElse("") ));
				System.out.println("========================================================");
			})
			.start();
	}

}
