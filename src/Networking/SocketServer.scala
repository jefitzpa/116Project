package Networking

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json}

class SocketServer extends Actor {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8000))

  var server: ActorRef = _
  var clients: Map[String, ActorRef] = Map()
  val game = new Game.Game

  override def receive: Receive = {

    case c: Connected =>
      println("SocketServer: Connected to Server")
      this.server = sender()
      this.server ! Register(self)

    case r: Received =>
      val parsed: JsValue = Json.parse(r.data.utf8String)

      println("SocketServer: Message from Server Received " + parsed)

      var Id = (parsed \ "userID").as[Int].toString
      if (Id.toInt == 0){
        val name = (parsed \ "username").as[String]
        Id = game.AddUser(name).userId.toString
  }

      val action = (parsed \ "action").as[String]

      val ClientActor = context.actorOf(Props(classOf[PlayerActor], Id))

      if (action == "connected"){
        this.clients = this.clients + (Id -> ClientActor)
        ClientActor ! Register(self)
        println("SocketServer: User "+ Id + " Has Connected")
      }
      if (action == "disconnected"){
        this.clients = this.clients - Id
      }
      if (action == "createPlayer"){
        val message = Id + "|/|"
        server ! Write(ByteString(message))
      }
  }

}

object SocketServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    val server = actorSystem.actorOf(Props(classOf[SocketServer]))
  }
}
