package datagen.models;

public class Order {
    private String symbol;
    private OrderType orderType;
    private Double price;
    private int quantity;

    public Order(String symbol, OrderType orderType, Double price, int quantity) {
        this.setSymbol(symbol);
        this.setOrderType(orderType);
        this.setPrice(price);
        this.setQuantity(quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return String.format("%s\t%s\t%f\t%d", this.symbol, this.orderType, this.price,
            this.quantity);
    }
}
