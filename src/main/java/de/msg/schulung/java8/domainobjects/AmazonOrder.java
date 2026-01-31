package de.msg.schulung.java8.domainobjects;

public class AmazonOrder {

	private String articleName;
	
	private Integer price;
	
	private String sender;
	
	private String orderNumber;

	public AmazonOrder() {
		// Defaultkonstruktor
	}

	public AmazonOrder(String articleName, Integer price, String sender, String orderNumber) {
		this.articleName = articleName;
		this.price = price;
		this.sender = sender;
		this.orderNumber = orderNumber;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	
}
