package de.msg.schulung.java8.orderprocess;

public abstract class AbstractOrderProcess {
	public boolean isGift;

	/**
	 * Führt die Auswahl der Ware durch.
	 */
	public abstract void doSelect();

	/**
	 * Erledigt den Bezahlvorgang.
	 */
	public abstract void doPayment();

	/**
	 * Führt die Auslieferung der Ware durch.
	 */
	public abstract void doDelivery();

	public void giftWrap() {
		System.out.println("Gift wrap done.");
	}

	public void setGift(boolean gift) {
		this.isGift = gift;
	}

	public final void processOrder() {
		doSelect();
		doPayment();
		if (isGift) {
			giftWrap();
		}
		doDelivery();
	}
}
