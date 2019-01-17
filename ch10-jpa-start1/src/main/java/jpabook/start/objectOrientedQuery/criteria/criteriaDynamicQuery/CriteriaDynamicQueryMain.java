package jpabook.start.objectOrientedQuery.criteria.criteriaDynamicQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.Team;

public class CriteriaDynamicQueryMain extends DataInit {

	/*
	 * 동적 쿼리
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("JPQL 동적 쿼리");
				/*
				 * 쿼리 문자열
				 */
				String queryStr = "SELECT m FROM CH10_OOQ_MEMBER m join m.team t";
				String condition = "";

				/*
				 * 조건 데이터
				 */
				Integer age = null;
				String userName = "sinnake1";
				String teamName = "우리반";
				
				/*
				 * 조건절 제어 하는 로직.
				 */
				condition += conditionFormat(age, "m.age = :age", condition);
				condition += conditionFormat(userName, "m.userName = :userName", condition);
				condition += conditionFormat(teamName, "t.name = :teamName", condition);

				condition = Optional.ofNullable(condition)
					.filter(c -> c.length() > 0)
					.map(c -> " where ")
					.orElse("") + condition;
				
				TypedQuery<Member> query = em.createQuery(queryStr + condition, Member.class);
				
				/*
				 * 조건절과 그에 응하는 값을 JPA에 바인드 하는 로직.
				 */
				setCondition(query, "age", age);
				setCondition(query, "userName", userName);
				setCondition(query, "teamName", teamName);
				
				/*
				 * 결과물 출력.
				 */
				query.getResultList().stream().forEach(m -> {
					System.out.println(String.format("member id : %s, member username : %s, member age : %s, team name : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge()
						, m.getTeam().getName() ));
				});
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("Crieria 동적 쿼리");
				/*
				 * 조건 데이터
				 */
				Integer age = null;
				String userName = "sinnake1";
				String teamName = "우리반";

				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				
				Root<Member> m = cq.from(Member.class);
				Join<Member, Team> t = m.join("team");
				
				/*
				 * 조건절 제어 하는 로직.
				 */
				List<Predicate> criteria = new ArrayList<>();
				criteriaSetCondition(m, m.<Integer>get("age"), age, criteria, cb, "age");
				criteriaSetCondition(m, m.<String>get("userName"), userName, criteria, cb, "userName");
				criteriaSetCondition(m, t.<String>get("name"), teamName, criteria, cb, "teamName");
	
				cq.select(cb.tuple(
					m.alias("m")
				))
				.where(cb.and(criteria.toArray(new Predicate[0]) ));				
				
				/*
				 * 조건절과 그에 응하는 값을 JPA에 바인드 하는 로직.
				 */
				TypedQuery<Tuple> tupleQuery = em.createQuery(cq);
				setCondition(tupleQuery, "age", age);
				setCondition(tupleQuery, "userName", userName);
				setCondition(tupleQuery, "teamName", teamName);
				
				/*
				 * 결과물 출력.
				 */
				tupleQuery.getResultList().stream().forEach(c -> {
					Member member = c.get("m", Member.class);
					
					System.out.println(String.format("member id : %s, member username : %s, member age : %s, team name : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge()
						, member.getTeam().getName() ));
				});
				
				print.mainEndPrint();
			})
			.start();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T, U, R, Z> void criteriaSetCondition(Root<T> root, Path<U> u, R r, List criteria, CriteriaBuilder cb, String key)  {
		Optional.ofNullable(r)
			.ifPresent(a -> criteria.add(cb.equal(u, cb.parameter(u.getJavaType(), key) )));
	}
	
	public static <T, U> TypedQuery<T> setCondition(TypedQuery<T> t, String key, U u) {
		Optional.ofNullable(u)
			.ifPresent(val -> t.setParameter(key, val));

		return t;
	}
	
	public static <T> String conditionFormat(T t, String conditionFormat, String condition) {
		return Optional.ofNullable(t)
			.map(c -> {
				return Optional.ofNullable(condition)
					.filter(con -> con.length() > 0)
					.map(con -> " and " + conditionFormat +  " ")
					.orElse(conditionFormat);
			})
			.orElse("");
	}

}
