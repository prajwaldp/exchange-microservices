from enum import Enum


class OrderSide(Enum):
    BUY = 1
    SELL = 2


class Order:
    def __init__(self, symbol: str, price: int, size: int, side: OrderSide, trader_id: int):
        self.symbol: str = symbol
        self.price: int = price
        self.size: int = size
        self.side: OrderSide = side
        self.trader_id: int = trader_id

    @classmethod
    def from_raw(cls, raw_str: str):
        return Order('AAPL', -1, -1, OrderSide.SELL, -1)

    def __repr__(self):
        return f'Order[symbol: {self.symbol}, price: {self.price}, size: {self.size}, side: {self.side}, trader_id: {self.trader_id}'
