from order_book_entry import OrderBookEntry


class PricePoint:
    def __init__(self):
        # Dummy head: orders are executed starting from the head
        self.head: OrderBookEntry = OrderBookEntry(-1, -1, -1)

        # Dummy tail: orders are added at the tail
        self.tail: OrderBookEntry = OrderBookEntry(-1, -1, -1)
