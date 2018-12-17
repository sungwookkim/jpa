package jpabook.start.primaryKey.sequence;

import jpabook.start.primaryKey.sequence.entity.SequenceBoard;
import jpabook.start.util.Logic;

public class SequenceMain {

	public static void main(String[] args) {
		
		new Logic().logic(em -> {
			SequenceBoard sequenceBoard = new SequenceBoard();			
			sequenceBoard.setData("A");
			
			SequenceBoard sequenceBoard1 = new SequenceBoard();			
			sequenceBoard1.setData("B");
			
			em.persist(sequenceBoard);
			em.persist(sequenceBoard1);
			
			em.createQuery("select b from SEQUENCE_BOARD b", SequenceBoard.class)
				.getResultList()
				.stream().forEach(m -> System.out.println("boards = " + m));		
		});

	}

}
