package gateway

import (
	"log"
	"net/http"
	"net/http/httputil"
	"strings"
)

type Server struct {
}

func Init(c *Config) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		ps := strings.Split(req.URL.Path, "/")
		var host string
		var path string

		for _, route := range c.Routes {
			if strings.Index("/"+ps[1], route.Filters) == 0 {
				host = route.Port
				path = route.Filters
				break
			}
		}

		log.Printf("Forwarding request to: host = %s, path = %s\n", host, path)

		rp := httputil.ReverseProxy{
			Director: func(request *http.Request) {
				request.URL.Scheme = "http"
				request.URL.Host = host
				request.URL.Path = path
				request.Host = host
			},
		}

		rp.ServeHTTP(w, req)
	}
}
