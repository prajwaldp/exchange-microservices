from typing import Optional


class OrderBookEntry:
    def __init__(self, price: int, size: int, seller_id: int):
        self.price: int = price
        self.size: int = size
        self.seller_id: int = seller_id
        self.next_: Optional[OrderBookEntry] = None
