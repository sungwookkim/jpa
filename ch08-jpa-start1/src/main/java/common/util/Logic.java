package common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Logic {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
	
	private Consumer<EntityManager> logic = null;
	private Consumer<EntityManager> commitAfter = null;
	private BiConsumer<EntityManager, EntityTransaction> biLlogic = null;	
	
	private JPA_AUTO jpa_AUTO = JPA_AUTO.CREATE;
	
	public Logic() { }
	public Logic(JPA_AUTO jpa_AUTO) {
		this.jpa_AUTO = jpa_AUTO;
	}
	
	public void start() {
		Map<String, Object> prop = new HashMap<String, Object>();

		prop.put("javax.persistence.jdbc.driver", "org.h2.Driver");
		prop.put("javax.persistence.jdbc.user", "sa");
		prop.put("javax.persistence.jdbc.password", "");
		prop.put("javax.persistence.jdbc.url", "jdbc:h2:tcp://localhost/~/test");

		prop.put("hibernate.hbm2ddl.auto", jpa_AUTO.getAutuConfig());
		prop.put("hibernate.show_sql", "true");
		prop.put("hibernate.format_sql", "true");
		prop.put("hibernate.use_sql_comments", "true");
		prop.put("hibernate.id.new_generator_mappings", "true");

		emf = Persistence.createEntityManagerFactory("jpabook", prop);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			Optional.ofNullable(biLlogic)
				.orElseGet(() -> {
					Optional.ofNullable(logic)
						.ifPresent(l -> {
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
