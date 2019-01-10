package jpabook.start.valueType.embeddedMapping;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.valueType.embeddedMapping.entity.Member;
import jpabook.start.valueType.embeddedMapping.entity.PhoneServiceProvider;
import jpabook.start.valueType.embeddedMapping.entity.embedded.Address;
import jpabook.start.valueType.embeddedMapping.entity.embedded.PhoneNumber;
import jpabook.start.valueType.embeddedMapping.entity.embedded.ZipCode;

public class EmbeddedMappingMain {

	public static void main(String[] args) {
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 임베디드 타입과 연관관계 저장 ===============");
				tx.begin();
				
				ZipCode zipCode = new ZipCode("sinnake zip", "sinnake plusFour");
				Address address = new Address("sinnake street", "sinnake city", "sinnake state", zipCode);

				ZipCode companyZipCode = new ZipCode("sinnake companyZip", "sinnake companyPlusFour");
				Address companyAddress = new Address("sinnake companyStreet", "sinnake companyCity", "sinnake companyState", companyZipCode);
				
				PhoneServiceProvider phoneServiceProvider = new PhoneServiceProvider("sinnake phoneServiceProvider");
				PhoneNumber phoneNumber = new PhoneNumber("sinnake areaCode", "sinnake localNumber", phoneServiceProvider);

				Member member = new Member(address, companyAddress, phoneNumber);
				
				em.persist(member);
				
				tx.commit();
				System.out.println("=============================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== 임베디드 타입과 연관관계 조회 ===============");
				Member member = Optional.ofNullable(em.find(Member.class, 1L)).orElseGet(Member::new) ;
				System.out.println(String.format("memberId : %s"
					, Optional.ofNullable(member.getId()).orElse(-1L) ));
				
				Address address = Optional.ofNullable(member.getAddress()).orElseGet(Address::new);
				System.out.println(String.format("addressCity : %s, addressState : %s, addressStreet : %s"
					, Optional.ofNullable(address.getCity()).orElse("")
					, Optional.ofNullable(address.getState()).orElse("")
					, Optional.ofNullable(address.getStreet()).orElse("") ));
				
				ZipCode zipCode = Optional.ofNullable(address.getZipCode()).orElseGet(ZipCode::new);
				System.out.println(String.format("zipCodePlusFour : %s, zipCodeZip : %s"
					, Optional.ofNullable(zipCode.getPlusFour()).orElse("")
					, Optional.ofNullable(zipCode.getZip()).orElse("") ));

				Address companyAddress = Optional.ofNullable(member.getCompanyAddres()).orElseGet(Address::new);
				System.out.println(String.format("companyAddressCity : %s, companyAddressState : %s, companyAddressStreet : %s"
					, Optional.ofNullable(companyAddress.getCity()).orElse("")
					, Optional.ofNullable(companyAddress.getState()).orElse("")
					, Optional.ofNullable(companyAddress.getStreet()).orElse("") ));
				
				ZipCode companyZipCode = Optional.ofNullable(companyAddress.getZipCode()).orElseGet(ZipCode::new);
				System.out.println(String.format("companyZipCodePlusFour : %s, companyZipCodeZip : %s"
					, Optional.ofNullable(companyZipCode.getPlusFour()).orElse("")
					, Optional.ofNullable(companyZipCode.getZip()).orElse("") ));
				
				PhoneNumber phoneNumber = Optional.ofNullable(member.getPhoneNumber()).orElseGet(PhoneNumber::new);
				System.out.println(String.format("phoneNumberAreaCode : %s, phoneNumberLocalNumber : %s"
					, Optional.ofNullable(phoneNumber.getAreaCode()).orElse("")
					, Optional.ofNullable(phoneNumber.getLocalNumber()).orElse("") ));
				
				PhoneServiceProvider phoneServiceProvider = Optional.ofNullable(phoneNumber.getPhoneServiceProvider())
					.orElseGet(PhoneServiceProvider::new);
				System.out.println(String.format("phoneServiceProviderName : %s"
					, Optional.ofNullable(phoneServiceProvider.getName()).orElse("") ));
				System.out.println("=============================================================");
			})
			.start();

	}

}
