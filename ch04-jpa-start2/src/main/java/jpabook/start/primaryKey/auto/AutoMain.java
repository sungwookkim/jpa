package jpabook.start.primaryKey.auto;

import common.util.Logic;
import jpabook.start.primaryKey.auto.entity.AutoBoard;

public class AutoMain {

	public static void main(String[] args) {
		
		new Logic()
			.logic(em -> {
				AutoBoard autoBoard = new AutoBoard();
				autoBoard.setData("A");
				
				AutoBoard autoBoard1 = new AutoBoard();
				autoBoard1.setData("B");
				
				em.persist(autoBoard);
				em.persist(autoBoard1);
			})
			.commitAfter(em -> {
				em.createQuery("select b from AUTO_BOARD b", AutoBoard.class)
				.getResultStream().forEach(m -> System.out.println("boards = " + m));				
			})
			.start();
	}
}
