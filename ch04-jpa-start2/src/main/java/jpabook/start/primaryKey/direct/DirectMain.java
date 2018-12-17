package jpabook.start.primaryKey.direct;

import jpabook.start.primaryKey.direct.entity.DirectBoard;
import jpabook.start.util.Logic;

public class DirectMain {

	public static void main(String[] args) {
	
		new Logic().logic((em) -> {
			DirectBoard directBoard = new DirectBoard();
			directBoard.setId(1);

			DirectBoard directBoard1 = new DirectBoard();
			directBoard1.setId(2);
			
			em.persist(directBoard);
			em.persist(directBoard1);
			
			em.createQuery("select b from DIRECT_BOARD b", DirectBoard.class)
				.getResultList()
				.stream().forEach(m -> System.out.println("boards = " + m));
		});
	}

}
