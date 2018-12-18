package jpabook.start.primaryKey.table;

import common.util.Logic;
import jpabook.start.primaryKey.table.entity.TableBoard;

public class TableMain {

	public static void main(String[] args) {
		
		new Logic()
			.logic(em -> {		
				TableBoard tableBoard = new TableBoard();
				tableBoard.setData("A");
				
				TableBoard tableBoard1 = new TableBoard();
				tableBoard1.setData("B");
				
				em.persist(tableBoard);
				em.persist(tableBoard1);
			})
			.commitAfter(em -> {
				em.createQuery("select b from TABLE_BOARD b", TableBoard.class)
					.getResultStream().forEach(m -> System.out.println("boards = " + m));
			})
			.start();
	}
}
