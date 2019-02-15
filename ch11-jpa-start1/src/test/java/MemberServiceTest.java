import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.embedded.Address;
import domain.entity.Member;
import repository.MemberRepository;
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

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	@Test
	public void join() throws Exception {
		// given
		Member member = new Member("sinnake", new Address("city", "street", "zipCode"));
		
		// when
		Long saveId = memberService.join(member);
		
		// then
		assertEquals(member.getId(), memberRepository.findoOne(saveId).getId());
	}
	
	@Test(expected = IllegalStateException.class)
	public void join_exception() throws IllegalStateException {
		Member member1 = new Member("sinnake", new Address("city", "street", "zipCode"));
		Member member2 = new Member("sinnake", new Address("city", "street", "zipCode"));
		
		memberService.join(member1);
		memberService.join(member2);
		
		fail("예외가 발생해야한다.");
	}
}
