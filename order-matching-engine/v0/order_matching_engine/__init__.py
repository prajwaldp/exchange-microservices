"""
In this implementation, the limit order book is represented using a flat linear
array (price_points), indexed by the numeric price value. Each
"""


import faust
from typing import List
from .order_book_entry import OrderBookEntry
from .order import Order, OrderSide
from .price_point import PricePoint
from .limit_order_book import LimitOrderBook
from .trade import Trade


class OrderMatchingEngine:

    def __init__(self, orders_topic: str = 'orders', symbols: List[str] = None):
        # Faust app
        self.app = faust.App(
            'order-matching-engine',
            broker='kafka://localhost:9092',
            value_serializer='raw'
        )
        self.orders_topic = self.app.topic(orders_topic)

        # Initialize the order books for the specified symbols
        # TODO Add support for inserting new symbols after starting the app
        self.order_books: dict[str, LimitOrderBook]

        if symbols:
            self.order_books = {symbol: LimitOrderBook(symbol)
                                for symbol in symbols}
        else:
            self.order_books = dict()

    def process_order(self, order_: Order) -> List[Trade]:
        if order_.side == OrderSide.BUY:
            return self.__process_buy_order(order_)
        else:
            assert order_.side == OrderSide.SELL
            return self.__process_sell_order(order_)

    def __process_buy_order(self, order_: Order) -> List[Trade]:
        return self.order_books[order_.symbol].try_buy(order_.price, order_.size, order_.trader_id)

    def __process_sell_order(self, order_: Order):
        return self.order_books[order_.symbol].try_sell(order_.price, order_.size, order_.trader_id)

    def start_listening(self):
        @self.app.agent(self.orders_topic)
        async def process_orders(orders):
            async for order_str in orders:
                orders_: List[Order] = Order.from_raw(order_str)
                for order_ in orders_:
                    self.process_order(order_)
