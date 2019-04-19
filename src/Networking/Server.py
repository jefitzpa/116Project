import eventlet
import socket
from flask import Flask, send_from_directory, request
from flask_socketio import SocketIO

eventlet.monkey_patch()

app = Flask(__name__)
socket_server = SocketIO(app)

userIDToSid = {}
sidToUserID = {}

scala_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
scala_socket.connect(('localhost', 8000))


@app.route("/")
def index():
    return send_from_directory('WebGui', 'Index.html')


@socket_server.on('register')
def RegisterPlayer():
    Id = getFromScala({"UserId": 0, "action": "createPlayer"})

    userIDToSid[Id] = request.sid
    sidToUserID[request.sid] = Id
    print(Id + " connected")
    message = {"userID": Id, "action": "connected"}
    SendToScala(message)


def SendToScala(message):


def getFromScala(request):
    return 0


socket_server.run(app, port=8080)




