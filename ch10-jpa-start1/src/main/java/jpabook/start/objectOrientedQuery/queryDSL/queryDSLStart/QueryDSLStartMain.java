package jpabook.start.objectOrientedQuery.queryDSL.queryDSLStart;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.QMember;
import jpabook.start.objectOrientedQuery.entity.Team;

public class QueryDSLStartMain extends DataInit {

	/*
	 * 시작
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("시작");
				JPAQuery<EntityManager> query = new JPAQuery<>(em);
				
				QMember qMember = new QMember("m");
				List<Tuple> members = query.select(qMember, qMember.team)
					.from(qMember)
					.where(qMember.userName.eq("sinnake1"))
					.orderBy(qMember.userName.desc())
					.fetch();
				
				members.stream().forEach(m -> {
					Member member = m.get(0, Member.class);
					Team team = m.get(1, Team.class);

					System.out.println(String.format("member id : %s, member userName : %s, member age : %s, member teamName : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge()
						, team.getName() ));
				});
				
				print.mainEndPrint();
			})
			.start();
	}

}
