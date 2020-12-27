package gateway

import (
	"encoding/json"
	"io/ioutil"
	"log"
)

type Config struct {
	Routes []Route
}

type Route struct {
	Filters      string
	Server       string
	AuthRequired bool `json:"auth_required"`
}

func NewConfigFromFile(filepath string) (*Config, error) {
	dat, err := ioutil.ReadFile(filepath)
	if err != nil {
		return nil, err
	}

	var c Config
	err = json.Unmarshal(dat, &c)
	if err != nil {
		return nil, err
	}

	log.Printf("Loaded config: %v", c)

	return &c, nil
}
