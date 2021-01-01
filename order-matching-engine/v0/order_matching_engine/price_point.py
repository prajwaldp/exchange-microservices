from typing import Optional
from .order_book_entry import OrderBookEntry


class PricePoint:
    def __init__(self, price: int):
        self.price: int = price
        # Dummy head: orders are executed starting from the head
        self.head: Optional[OrderBookEntry] = None
        # Dummy tail: orders are added at the tail
        self.tail: Optional[OrderBookEntry] = None

    def insert(self, order_size: int, trader_id: int):
        entry = OrderBookEntry(self.price, order_size, trader_id)
        if self.head:
            self.tail.next_ = entry
        else:
            self.head = entry
        self.tail = entry
