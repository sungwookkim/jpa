package common.util;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Logic {
	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
	
	private Consumer<EntityManager> logic = null;
	private Consumer<EntityManager> commitAfter = null;
	private BiConsumer<EntityManager, EntityTransaction> biLlogic = null;
	
	public Logic() { }
		
	public void start() {	
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			Optional.ofNullable(biLlogic)
				.orElseGet(() -> {
					Optional.ofNullable(logic)
						.ifPresent(logic -> {
							tx.begin();
							logic.accept(em);
							tx.commit();
						});
					
					return (e, t) -> {};
				})
				.accept(em, tx);
			
			Optional.ofNullable(commitAfter).orElse(e -> {}).accept(em);
			
		} catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
		emf.close();
	}
	
	public Logic commitAfter(Consumer<EntityManager> commitAfter) {
		this.commitAfter = commitAfter;

		return this;
	}
	
	public Logic logic(Consumer<EntityManager> logic) {
		this.logic = logic;
		
		return this;
	}
	
	public Logic logic(BiConsumer<EntityManager, EntityTransaction> biLlogic) {
		this.biLlogic = biLlogic;
		
		return this;
	}
}
