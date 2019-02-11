package domain.entity;

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

import domain.embedded.Address;
import domain.entity.status.DeliveryStatus;

@Entity(name = "CH11_DELIVERY")
@SequenceGenerator(
	name = "CH11_DELIVERY_SEQUENCE"
	, sequenceName = "CH11_DELIVERY_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Delivery {

	@Id
	@Column(name = "DELIVERY_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH11_DELIVERY_SEQ")
	private long id;
	
	@OneToOne(mappedBy = "delivery")
	private Order order;

	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	
	public Delivery() { }
	
	public Delivery(Address address, DeliveryStatus status) {
		this.address = address;
		this.status = status;
	}

	public long getId() { return id; }

	public Order getOrder() { return order; }
	public void setOrder(Order order) { 
		this.order = order;
		
		if(order.getDelivery() != this) {
			order.setDelivery(this);
		}
	}

	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }

	public DeliveryStatus getStatus() { return status; }
	public void setStatus(DeliveryStatus status) { this.status = status; }
}
