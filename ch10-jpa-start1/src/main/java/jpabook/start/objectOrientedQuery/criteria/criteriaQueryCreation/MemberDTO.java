package jpabook.start.objectOrientedQuery.criteria.criteriaQueryCreation;

public class MemberDTO {

	private String userName;
	private Integer age;
	
	public MemberDTO(String userName, Integer age) {
		this.userName = userName;
		this.age = age;
	}

	public String getUserName() { return userName; }

	public Integer getAge() { return age; }
}
