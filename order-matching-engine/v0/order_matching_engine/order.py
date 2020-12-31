from enum import Enum


class OrderSide(Enum):
    BUY = 1
    SELL = 2


class Order:
    def __init__(self, symbol: str, price: int, size: int, side: OrderSide):
        self.symbol: str = symbol
        self.price: int = price
        self.size: int = size
        self.side: OrderSide = side

    @classmethod
    def from_raw(cls, raw_str: str):
        return Order('AAPL', -1, -1, OrderSide.SELL)
