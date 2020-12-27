package gateway

import (
	"github.com/prajwaldp/exchange-microservices/gateway-server-v2/auth"
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
		var authRequired bool

		for _, route := range c.Routes {
			if strings.Index("/"+ps[1], route.Filters) == 0 {
				host = route.Server
				path = route.Filters
				authRequired = route.AuthRequired
				break
			}
		}

		log.Printf("Forwarding request to: host = %s, path = %s\n", host, path)

		if authRequired {
			isAuthenticated := auth.VerifyJWT()
			if !isAuthenticated {
				w.WriteHeader(403)
				return
			}
		}

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
