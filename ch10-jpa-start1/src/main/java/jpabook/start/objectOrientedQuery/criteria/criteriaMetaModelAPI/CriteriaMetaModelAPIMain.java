package jpabook.start.objectOrientedQuery.criteria.criteriaMetaModelAPI;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.Member_;

public class CriteriaMetaModelAPIMain extends DataInit {

	/*
	 * Criteria 메타 모델 API
	 * 
	 * Criteria는 코드 기반으므로 컴파일 시점에 오류를 발견할 수 있다. 하지만 m.get("age")에서 age는 문자다.
	 * 'age' 대신에 'ageaaaa' 이렇게 잘못 적어도 컴파일 시점에 에러를 발견하지 못한다.
	 * 따라서 완전한 코드 기반이라 할 수 없다.
	 * 이런 부분까지 코드로 작성하려면 메타 모델 API를 사용하면 된다. 메타 모델 API를 사용하려면 먼저 
	 * 메타 모델 클래스를 만들어야 한다. 
	 * 
	 * 메타 모델 같이 복잡한 코드를 개발자가 직접 작성하지 않고 자동 생성기가 엔티티 클래스를 기반으로
	 * 메타 모델 클래스를 만들어준다. 
	 * 하이버네이트 구현체를 사용하면 코드 생성기는 org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor를
	 * 사용하면 된다.
	 * 
	 * 코드 생성기는 모든 엔티티 클래스를 찾아서 "엔티티명_.java" 모양의 메타 모델 클래스를 생성해준다.
	 * 위치는 target/generated-sources/annotations/폴더 하위에 생성 된다.
	 * 
	 * 코드 생성기 설정 
	 * 코드 생성기는 보통 메이븐이나 엔트, 그래들(Gradle) 같은 빌드 도구를 사용해서 실행한다.
	 * 메이븐 기준으로
	 * 
	 * <dependency>
		    <groupId>org.hibernate</groupId>
		    <artifactId>hibernate-jpamodelgen</artifactId>
		    <version>5.4.0.Final</version>
		    <scope>provided</scope>
		</dependency>
		
	 * 관련 빌드 설정은
	 *	<build>
			<plugins>
				<plugin>
					<artifactId>maven-compiler-plugin</artifactId>
					<configuration>
						<source>1.8</source>
						<target>1.8</target>
						<compilerArguments>
							<processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
						</compilerArguments>
					</configuration>
				</plugin>
			</plugins>
		</build>
	 * 로 설정하였다.
	 */
	public static void main(String[] args) {

		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("Criteria 메타 모델 API");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				Root<Member> m = cq.from(Member.class);
				
				subPrint.subStartPrint("메타 모델 API 적용 전");
				cq.select(cb.tuple(
					m.alias("m")
				))
				.where(cb.gt(m.<Integer>get("age"), 30))
				.orderBy(cb.desc(m.get("age")));
				
				em.createQuery(cq).getResultList().stream().forEach(t -> {
					Member member = t.get("m", Member.class);
					
					System.out.println(String.format("member id : %s, member username : %s, member age : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge() ));
				});
				subPrint.subEndPrint();
				 
								
				
				/*
				 * 책 기준으로 하면 메타파일은 생성은 되나 import가 제대로 안되서
				 * 클래스를 찾을수 없다. 이에 관련해서 아래 링크를 참고해서 추가 설정을 하면 된다.
				 * 
				 * http://millky.com/@origoni/post/874
				 */
				subPrint.subStartPrint("메타 모델 API 적용 후");
				cq = cb.createTupleQuery();
				m = cq.from(Member.class);

				cq.select(cb.tuple(
					m.alias("m")
				))
				// 메타 모델 적용
				.where(cb.gt(m.get(Member_.age), 37))
				.orderBy(cb.desc(m.get(Member_.age)));
				
				em.createQuery(cq).getResultList().stream().forEach(t -> {
					Member member = t.get("m", Member.class);
					
					System.out.println(String.format("member id : %s, member username : %s, member age : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge() ));
				});
				subPrint.subEndPrint();

				print.mainEndPrint();
			})
			.start();
	}

}
