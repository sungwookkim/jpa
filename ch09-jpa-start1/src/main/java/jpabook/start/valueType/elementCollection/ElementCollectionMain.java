package jpabook.start.valueType.elementCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.valueType.elementCollection.entity.Member;
import jpabook.start.valueType.elementCollection.entity.embedded.Address;

public class ElementCollectionMain {

	public static void main(String[] args) {
		
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("@ElementCollection 저장");
				tx.begin();
				
				Member member = new Member();
				member.setAddress(new Address("sinnake city", "sinnake street", "sinnake zipCode"));
				
				member.addFavoriteFoods("라면");
				member.addFavoriteFoods("김치부친개");
				member.addFavoriteFoods("밥");
				
				member.addAddresses(new Address("sinnake city1", "sinnake street1", "sinnake zipCode1"));
				member.addAddresses(new Address("sinnake city2", "sinnake street2", "sinnake zipCode2"));
				member.addAddresses(new Address("sinnake city3", "sinnake street3", "sinnake zipCode3"));

				em.persist(member);
				
				tx.commit();
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("@ElementCollection 조회");
				Member member = em.find(Member.class, 1L);
				
				Address address = Optional.ofNullable(member.getAddress()).orElseGet(Address::new);
				System.out.println(String.format("city : %s, street : %s, zipCode : %s"
					, Optional.ofNullable(address.getCity()).orElse("")
					, Optional.ofNullable(address.getStreet()).orElse("")
					, Optional.ofNullable(address.getZipCode()).orElse("") ));
				
				Set<String> favoriteFoods = Optional.ofNullable(member.getFavoriteFoods()).orElseGet(HashSet::new);
				favoriteFoods.stream().forEach(f -> {
					System.out.println("favoriteFood : " + f);
				});
				
				List<Address> addresses = Optional.ofNullable(member.getAddresses()).orElseGet(ArrayList::new);
				addresses.stream().forEach(a -> {
					System.out.println(String.format("city : %s, street : %s, zipCode : %s"
						, Optional.ofNullable(a.getCity()).orElse("")
						, Optional.ofNullable(a.getStreet()).orElse("")
						, Optional.ofNullable(a.getZipCode()).orElse("") ));
				});
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				print.mainStartPrint("@ElementCollection 수정");
				tx.begin();
				
				Member member = em.find(Member.class, 1L);
				/*
				 * UPDATE문이 실행 됨.
				 */
				member.setAddress(new Address("city 바뀜", "street 바뀜", "zipCode 바뀜"));
				
				/*
				 * 값 타입 컬렉션인 경우에는 UPDATE가 아니라 DELETE문이 실행 된 후에 컬렉션에 있는 데이터를
				 * 다시 전부다 INSERT를 함.
				 */
				Set<String> favoriteFoods = Optional.ofNullable(member.getFavoriteFoods()).orElseGet(HashSet::new);
				favoriteFoods.remove("김치");
				favoriteFoods.add("김치 안좋아함");
				
				List<Address> addresses = Optional.ofNullable(member.getAddresses()).orElseGet(ArrayList::new);
				addresses.remove(new Address("sinnake city1", "sinnake street1", "sinnake zipCode1"));
				addresses.add(new Address("sinnake city4", "sinnake street4", "sinnake zipCode4"));
				
				tx.commit();
				print.mainEndPrint();
			})
			.start();
			
	}
}
