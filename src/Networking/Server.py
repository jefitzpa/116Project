import eventlet
import socket
from flask import Flask, send_from_directory
from flask_socketio import SocketIO

eventlet.monkey_patch()

app = Flask(__name__)
socket_server = SocketIO(app)

usernameToSid = {}
sidToUsername = {}

scala_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
scala_socket.connect(('localhost', 8000))


@app.route("/")
def index():
    return send_from_directory('WebGui', 'Index.html')




