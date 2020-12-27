package gateway

import (
	"encoding/json"
	"io/ioutil"
)

type Config struct {
	Routes []Route
}

type Route struct {
	Filters string
	Port    string
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

	return &c, nil
}
