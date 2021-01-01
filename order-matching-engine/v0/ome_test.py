from typing import List
from order_matching_engine import OrderMatchingEngine
from order_matching_engine.order import Order, OrderSide
from order_matching_engine.trade import Trade


orders: dict[str: Order] = {
    '1s100': Order("AAPL", 101, 100, OrderSide.SELL, 1),
    '2s100': Order("AAPL", 101, 100, OrderSide.SELL, 2),
    '1b100': Order("AAPL", 101, 100, OrderSide.BUY, 1),
    '2b100': Order("AAPL", 101, 100, OrderSide.BUY, 2),

    '1s50': Order("AAPL", 101, 50, OrderSide.SELL, 1),
    '2s50': Order("AAPL", 101, 50, OrderSide.SELL, 2),
    '1b50': Order("AAPL", 101, 50, OrderSide.BUY, 1),
    '2b50': Order("AAPL", 101, 50, OrderSide.BUY, 2),

    '1s25': Order("AAPL", 101, 25, OrderSide.SELL, 1),
    '2s25': Order("AAPL", 101, 25, OrderSide.SELL, 2),
    '1b25': Order("AAPL", 101, 25, OrderSide.BUY, 1),
    '2b25': Order("AAPL", 101, 25, OrderSide.BUY, 2),
}


def test_basic_buy_sell():
    engine = OrderMatchingEngine('orders', ['AAPL'])

    engine.process_order(orders['1s100'])
    executed_trades = engine.process_order(orders['2b100'])

    assert __is_trades_equivalent(
        executed_trades,
        [Trade('AAPL', 1, 2, 101, 100)]
    )


def test_buy_sell_reordered():
    engine = OrderMatchingEngine('orders', ['AAPL'])

    engine.process_order(orders['2b100'])
    executed_trades = engine.process_order(orders['1s100'])

    assert __is_trades_equivalent(
        executed_trades,
        [Trade('AAPL', 1, 2, 101, 100)]
    )


def test_partial_fill_1():
    engine = OrderMatchingEngine('orders', ['AAPL'])
    engine.process_order(orders['1s100'])
    executed_trades = engine.process_order(orders['2b50'])
    assert __is_trades_equivalent(
        executed_trades,
        [Trade('AAPL', 1, 2, 101, 50)]
    )


def test_partial_fill_2():
    engine = OrderMatchingEngine('orders', ['AAPL'])
    engine.process_order(orders['1s50'])
    executed_trades = engine.process_order(orders['2b100'])
    assert __is_trades_equivalent(
        executed_trades,
        [Trade('AAPL', 1, 2, 101, 50)]
    )


def test_incremental_over_fill():
    engine = OrderMatchingEngine('orders', ['AAPL'])
    orders_ = ['1b100', '2s25', '2s25', '1s50']
    expected_trades_ = [
        [],
        [Trade('AAPL', 2, 1, 101, 25)],
        [Trade('AAPL', 2, 1, 101, 25)],
        [Trade('AAPL', 1, 1, 101, 50)]
    ]

    for o, e in zip(orders_, expected_trades_):
        assert __is_trades_equivalent(engine.process_order(orders[o]), e)


def __is_trades_equivalent(actual: List[Trade], expected: List[Trade]) -> bool:
    if len(actual) != len(expected):
        return False

    for a, e in zip(actual, expected):
        if not __is_trade_equivalent(a, e):
            return False

    return True


def __is_trade_equivalent(actual: Trade, expected: Trade) -> bool:

    if actual.symbol != expected.symbol:
        return False

    if actual.seller_id != expected.seller_id:
        return False

    if actual.buyer_id != expected.buyer_id:
        return False

    if actual.price != expected.price:
        return False

    if actual.size != expected.size:
        return False

    return True
