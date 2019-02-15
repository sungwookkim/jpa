package repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import domain.entity.Member;

@Repository
public class MemberRepositoryNotSpringData {

	@PersistenceContext
	EntityManager em;
	
	public Member findOne(Long id) {
		return em.createQuery("SELECT m FROM CH15_MEMBER m WHERE m.id = :id", Member.class)
			.setParameter("id", id)
			.getSingleResult();
	}
	
	public Member findMember(Long id) throws NoResultException {
		return this.findOne(id);
	}
}
