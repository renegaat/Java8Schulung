package de.msg.schulung.java8.orderprocess;

public class StoreOrderProcess extends AbstractOrderProcess {

	@Override
	public void doSelect() {
		System.out.println("Customer chooses the item from shelf.");
	}

	@Override
	public void doPayment() {
		System.out.println("Pays at counter through cash/POS");
	}

	@Override
	public void doDelivery() {
		System.out.println("Item deliverd to in delivery counter.");
	}

}

