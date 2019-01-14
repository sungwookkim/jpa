package jpabook.start.objectOrientedQuery.jpql.setOrder;

public class SetDTO {

	private String name;
	private Long count;
	private Long max;
	private Double avg;
	private Integer sum;
	private Integer min; 
	
	public SetDTO(String name, Long count, Long max, Double avg, Integer sum, Integer min) {
		this.name = name;
		this.count = count;
		this.max = max;
		this.avg = avg;
		this.sum = sum;
		this.min = min;
	}

	public String getName() { return name; }

	public Long getCount() { return count; }
	
	public Long getMax() { return max; }
	
	public Double getAvg() { return avg; }
	
	public Integer getSum() { return sum; }
	
	public Integer getMin() { return min; }
}
