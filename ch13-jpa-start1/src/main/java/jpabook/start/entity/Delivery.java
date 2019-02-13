package jpabook.start.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

import jpabook.start.entity.embedded.Address;
import jpabook.start.entity.superClass.BaseEntity;

@Entity(name = "CH14_DELIVERY")
@SequenceGenerator(
	name = "CH14_DELIVERY_SEQUENCE"
	, sequenceName = "CH14_DELIVERY_SEQ"
	, allocationSize = 1
	, initialValue = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Delivery extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH14_DELIVERY_SEQ")
	@Column(name = "DELIVERY_ID")
	private long id;	
	
	@OneToOne(mappedBy = "delivery")	
	private Order order;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;
	
	public Delivery() { }
	
	public Delivery(Address address, DeliveryStatus deliveryStatus) {
		this.address = address;
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
	
	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }
	
	public DeliveryStatus getDeliveryStatus() { return deliveryStatus; }
	public void setDeliveryStatus(DeliveryStatus deliveryStatus) { this.deliveryStatus = deliveryStatus; }
}
