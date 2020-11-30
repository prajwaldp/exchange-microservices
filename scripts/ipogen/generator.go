package main

import (
	"bytes"
	"encoding/csv"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
	"os"
	"strconv"
)

const baseURL string = "http://localhost:8002"

// Bid is a buy order
type Bid struct {
	InitiaterID    string  `json:"initiaterId"`
	TickerSymbol   string  `json:"tickerSymbol"`
	NumberOfShares float64 `json:"numberOfShares"`
	BidPrice       float64 `json:"bidPrice"`
	Country        string  `json:"country"`
}

// Ask is a sell order
type Ask struct {
	InitiaterID    string  `json:"initiaterId"`
	TickerSymbol   string  `json:"tickerSymbol"`
	NumberOfShares float64 `json:"numberOfShares"`
	AskPrice       float64 `json:"askPrice"`
	Country        string  `json:"country"`
}

// Hello returns a greeting for a named person
func Hello(name string) string {
	message := fmt.Sprintf("Hi, %v. Welcome!", name)
	return message
}

func releaseShares() {
	csvfile, err := os.Open("ipo.csv")
	if err != nil {
		log.Fatal(err)
	}
	defer csvfile.Close()

	r := csv.NewReader(csvfile)

	for {
		record, err := r.Read()
		if err == io.EOF {
			break
		}
		if err != nil {
			log.Fatal(err)
		}

		numberOfshares, err := strconv.ParseFloat(record[2], 64)
		if err != nil {
			log.Fatal(err)
		}

		askPrice, err := strconv.ParseFloat(record[3], 64)
		if err != nil {
			log.Fatal(err)
		}

		ask := &Ask{
			InitiaterID:    record[0],
			TickerSymbol:   record[1],
			NumberOfShares: numberOfshares,
			AskPrice:       askPrice,
			Country:        record[4],
		}

		requestBody, _ := json.Marshal(ask)
		log.Printf("POST %s\n", string(requestBody))
		response, _ := http.Post(baseURL+"/asks", "application/json", bytes.NewBuffer(requestBody))
		log.Print(response.Status)
	}
}

func main() {
	fmt.Printf("Making requests to %s\n", baseURL)
	releaseShares()
}
