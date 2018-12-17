package jpabook.start.primaryKey.identity;

import jpabook.start.primaryKey.identity.entity.IdentityBoard;
import jpabook.start.util.Logic;

public class IdentityMain {

	public static void main(String[] args) {

		new Logic().logic(em -> {
			IdentityBoard identityBoard = new IdentityBoard();
			identityBoard.setData("A");
			
			IdentityBoard identityBoard1 = new IdentityBoard();
			identityBoard1.setData("B");
			
			em.persist(identityBoard);
			em.persist(identityBoard1);
			
			em.createQuery("select b from IDENTITY_BOARD b", IdentityBoard.class)
				.getResultList()
				.stream().forEach(m -> System.out.println("boards = " + m));			
		});
	}
}
