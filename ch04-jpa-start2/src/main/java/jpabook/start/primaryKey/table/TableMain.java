package jpabook.start.primaryKey.table;

import java.util.List;

import jpabook.start.primaryKey.table.entity.TableBoard;
import jpabook.start.util.Logic;

public class TableMain {

	public static void main(String[] args) {
		
		new Logic().logic(em -> {		
			TableBoard tableBoard = new TableBoard();
			tableBoard.setData("A");
			
			TableBoard tableBoard1 = new TableBoard();
			tableBoard1.setData("B");
			
			em.persist(tableBoard);
			em.persist(tableBoard1);
			
			List<TableBoard> boards = em.createQuery("select b from TABLE_BOARD b", TableBoard.class)
				.getResultList();

			boards.stream().forEach(m -> System.out.println("boards = " + m));
		});
	}
}
