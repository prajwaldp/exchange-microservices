# A Custom Gateway Server

## Instructions
- Use the files `routes.json` to configure the gateway server.
- Run the testing backend with the command

```sh
$ FLASK_APP=test_backend.py flask run -p 8081
$ FLASK_APP=test_backend.py flask run -p 8082
```

- Start the server with `go run main.go`

## To Do

1. Load balancing
    - Start with round robin
2. JWT check for endpoints that require authorization
    - Start with one role that requires authentication
    - Expand to more roles
3. More control over endpoints in the routes configuration

