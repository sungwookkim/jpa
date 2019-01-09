package model5.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import model5.entity.superClass.BaseEntity;

@Entity(name = "CH08_MODEL5_DELIVERY")
@SequenceGenerator(
	name = "CH08_MODEL5_DELIVERY_SEQUENCE"
	, sequenceName = "CH08_MODEL5_DELIVERY_SEQ"
	, allocationSize = 1
	, initialValue = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Delivery extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH08_MODEL5_DELIVERY_SEQ")
	@Column(name = "DELIVERY_ID")
	private long id;	
	
	@OneToOne(mappedBy = "delivery")	
	private Order order;
	
	private String city;
	private String street;
	private String zipCode;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;
	
	public Delivery() { }
	
	public Delivery(String city, String street, String zipCode, DeliveryStatus deliveryStatus) {
		this.city = city;
		this.street = street;
		this.zipCode = zipCode;
		this.deliveryStatus = deliveryStatus;
	}

	public long getId() { return id; }
	
	public Order getOrder() { return this.order; }
	public void setOrder(Order order) {
		this.order = order;

		if(order.getDelivery() != this) {
			order.setDelivery(this);	
		}
	}
	
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	
	public String getStreet() { return street; }	
	public void setStreet(String street) { this.street = street; }
	
	public String getZipCode() { return zipCode; }
	
	public void setZipCode(String zipCode) { this.zipCode = zipCode; }
	
	public DeliveryStatus getDeliveryStatus() { return deliveryStatus; }
	public void setDeliveryStatus(DeliveryStatus deliveryStatus) { this.deliveryStatus = deliveryStatus; }
}
