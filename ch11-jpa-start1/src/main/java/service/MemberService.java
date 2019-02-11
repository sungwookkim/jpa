package service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.entity.Member;
import repository.MemberRepository;

@Service
@Transactional
public class MemberService {

	@Autowired
	MemberRepository memberRepository;
	
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		
		return member.getId();
	}
	
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	public Member findOne(Long id) {
		return memberRepository.findoOne(id);
	}

	private void validateDuplicateMember(Member member) {
		List<Member> members = memberRepository.findByName(member.getName());

		Optional.ofNullable(members)
			.filter(m -> !m.isEmpty())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 회원입니다.");				
			});
	}
}
