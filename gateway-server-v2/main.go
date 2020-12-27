package main

import (
	"github.com/prajwaldp/exchange-microservices/gateway-server-v2/gateway"
	"log"
	"net/http"
)

func main() {
	port := "8080"
	config, err := gateway.NewConfigFromFile("routes.json")
	if err != nil {
		log.Fatal(err)
	}
	log.Print(config)
	http.HandleFunc("/", gateway.Init(config))
	log.Printf("Listening for requests on port %v\n", port)
	log.Fatal(http.ListenAndServe(":" + port, nil))
}
