import eventlet
import json
import socket
from flask import Flask, send_from_directory, request
from flask_socketio import SocketIO
from threading import Thread

eventlet.monkey_patch()

app = Flask(__name__)
socket_server = SocketIO(app)

userIDToSid = {}
sidToUserID = {}

currentPlyId = 0

scala_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
scala_socket.connect(('localhost', 8000))


def listenForData(the_socket):
    delimiter = "|/|"
    buffer = ""
    while True:
        buffer += the_socket.recv(1024).decode()
        while delimiter in buffer:
            message = buffer[:buffer.find(delimiter)]
            buffer = buffer[buffer.find(delimiter) + 1:]
            getFromScala(message)


Thread(target=listenForData, args=(scala_socket,)).start()


@app.route('/')
def index():
    return send_from_directory('WebGui', 'Index.html')


@socket_server.on('register')
def RegisterPlayer(username, id):
    print("Server: Player Connecting")

    userIDToSid[id] = request.sid
    sidToUserID[request.sid] = id
    print("Server: " + str(id) + " connected")

    message = {"userID": id, "action": "connected", "username": username}
    SendToScala(message)


@socket_server.on('UpdatePlayers')
def SendPlayers():
    return 0 ##return the map of players with new locations from database


@socket_server.on('PlayerMovement')
def UpdateLocation(Id):
    return 0 ##go into database and update the players location


def SendToScala(message):
    scala_socket.sendall(json.dumps(message).encode())


def getFromScala(message):
    message = json.loads(message)
    global currentPlyId
    currentPlyId = message
    print(currentPlyId)


socket_server.run(app, port=8080)




