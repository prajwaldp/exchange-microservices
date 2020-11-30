package datagen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datagen.controllers.OrdersController;
import datagen.models.Order;
import datagen.models.OrderType;

public class RandomFeed {
    private static final String TAB_DELIMITER = "\\t";
    private static List<String> symbols = new ArrayList<>();
    private static Map<String, Double> prices = new HashMap<>();
    private static Random rand = new Random(42L);

    private static Map<Integer, OrderType> orderTypes = new HashMap<>();
    static {
        orderTypes.put(0, OrderType.BUY);
        orderTypes.put(1, OrderType.SELL);
    }

    public static void main(String[] args) {
        buildSymbols();

        for (int i = 0; i < 50; i++) {
            run();
        }
    }

    static void run() {
        int nSymbols = symbols.size();
        int idx = rand.nextInt(nSymbols);
        String symbol = symbols.get(idx);
        Double price = prices.get(symbol);
        double delta = Math.random();
        int higher = rand.nextBoolean() ? 1 : -1;
        double myPrice = price + (higher) * (delta / 100.0) * price;
        OrderType orderType = orderTypes.get(rand.nextInt(2));
        int quantity = rand.nextInt(1000);
        Order o = new Order(symbol, orderType, myPrice, quantity);
        OrdersController.submit(o);
    }

    static void buildSymbols() {
        Logger log = LoggerFactory.getLogger(Random.class);
        
        URL inputFileURL = RandomFeed.class.getClassLoader().getResource("dow.txt");
        File inputFile = null;
        try {
            inputFile = new File(inputFileURL.toURI());
        } catch (URISyntaxException e) {
            log.error("Failed to parse URL {} to URI", inputFileURL);
            System.exit(0);
        }
        
        log.info("Reading {}", inputFile);
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            log.error("File {} not found", inputFile);
            System.exit(0);
        }

        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(TAB_DELIMITER);
                String symbol = values[0];
                double price = Double.parseDouble(values[2]);
                symbols.add(symbol);
                prices.put(symbol, price);
            }
        } catch (IOException e) {
            System.err.println(e);
        } catch (NumberFormatException e) {
            System.err.println(e);
        }
    }
}
