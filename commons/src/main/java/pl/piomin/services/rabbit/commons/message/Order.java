package pl.piomin.services.rabbit.commons.message;

import java.io.Serializable;

public class Order implements Serializable {

	private static final long serialVersionUID = -5810415223475626164L;
	
	private Integer id;
	private String description;
	private OrderType type;

	public Order() {

	}

	public Order(Integer id, String description, OrderType type) {
		this.id = id;
		this.description = description;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("Order[id=%d, type=%s]", id, type);
	}
}
