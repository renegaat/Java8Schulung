package de.msg.schulung.java8.domainobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomannc on 23.02.2017.
 */
public class AmazonOrderFactory {

    public static List<AmazonOrder> createAmazonOrderList() {

        final List<AmazonOrder> orders = new ArrayList<>();

        orders.add(new AmazonOrder("DELL Notebook", 500, "Müller", "4711"));
        orders.add(new AmazonOrder("Klangwerk", 14, "Meier", "101"));
        orders.add(new AmazonOrder("101", 20, "Schulze", "42"));
        orders.add(new AmazonOrder("Tapestry", 40, "Schmidt", "5001"));
        orders.add(new AmazonOrder("Spring", 45, "Müller", "41003"));

        return orders;
    }
}
