from flask import Flask, make_response, request
import json
app = Flask(__name__)

@app.route('/<path:path>')
def hello_world(path):
    dat = json.dumps({k:v for k, v in request.headers})
    resp = make_response(f"raw data: \n{dat} \n\n You made a request to {path}", 200)
    resp.mimetype = "text/plain"
    return resp
