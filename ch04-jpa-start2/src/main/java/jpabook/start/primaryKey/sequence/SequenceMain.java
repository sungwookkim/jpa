package jpabook.start.primaryKey.sequence;

import common.util.Logic;
import jpabook.start.primaryKey.sequence.entity.SequenceBoard;

public class SequenceMain {

	public static void main(String[] args) {
		
		new Logic()
			.logic(em -> {
				SequenceBoard sequenceBoard = new SequenceBoard();			
				sequenceBoard.setData("A");
				
				SequenceBoard sequenceBoard1 = new SequenceBoard();			
				sequenceBoard1.setData("B");
				
				em.persist(sequenceBoard);
				em.persist(sequenceBoard1);
			})
			.commitAfter(em -> {
				em.createQuery("select b from SEQUENCE_BOARD b", SequenceBoard.class)
					.getResultStream().forEach(m -> System.out.println("boards = " + m));
			})
			.start();
	}

}
