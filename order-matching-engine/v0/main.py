from order_matching_engine import OrderMatchingEngine


if __name__ == '__main__':
    ome = OrderMatchingEngine()
    ome.start_listening()
    ome.app.main()
