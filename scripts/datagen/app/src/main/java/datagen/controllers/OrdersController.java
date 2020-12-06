package datagen.controllers;

import datagen.KafkaAbstraction;
import datagen.models.Order;
import datagen.models.OrderType;
import datagen.generated.OrderOuterClass;

public class OrdersController {

	/**
	 * Submits the order to the Kafka topic
	 * @param o The order to be pushed.
	 */
	public static void submit(Order o) {
		String key = o.getSymbol();
		
		int orderTypeValue = o.getOrderType() == OrderType.BUY ? 0 : 1;

		OrderOuterClass.Order msg = OrderOuterClass.Order.newBuilder()
			.setSymbol(o.getSymbol())
			.setPrice(o.getPrice())
			.setOrderTypeValue(orderTypeValue)
			.setQuantity(o.getQuantity())
			.build();

		KafkaAbstraction.send(key, msg.toString());
	}
}
