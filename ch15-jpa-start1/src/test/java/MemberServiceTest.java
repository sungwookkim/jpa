import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repository.MemberRepository;
import repository.MemberRepositoryNotSpringData;
import service.MemberService;
import web.db.DBConfig;
import web.db.JpaConfig;
import web.db.vendor.h2.H2Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
classes = {
	H2Config.class
	, DBConfig.class
	, JpaConfig.class
	, AppConfig.class
})
@Transactional
public class MemberServiceTest {

	@PersistenceContext EntityManager em;
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired MemberRepositoryNotSpringData memberRepositoryNotSpringData;

	@Test(expected = EmptyResultDataAccessException.class)
	public void JPA예외변환기적용_EmptyResultDataAccessException() throws EmptyResultDataAccessException {
		memberRepositoryNotSpringData.findOne(10L);
		
		fail("조회된 엔티티가 없으므로 EmptyResultDataAccessException 예외가 발생 되어야 한다.");
	}
	
	@Test(expected = NoResultException.class)
	public void JPA예외변환기적용_기본NotResultException() throws NoResultException {
		memberRepositoryNotSpringData.findMember(10L);
		
		fail("조회된 엔티티가 없으므로 NoResultException 예외가 발생 되어야 한다.");
	}
}
