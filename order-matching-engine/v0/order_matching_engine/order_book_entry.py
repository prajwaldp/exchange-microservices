from typing import Optional


class OrderBookEntry:
    def __init__(self, price: int, size: int, trader_id: int):
        self.price: int = price
        self.size: int = size
        self.trader_id: int = trader_id
        self.next_: Optional[OrderBookEntry] = None
