package Networking

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json}

case class Update()

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
      Database.SetupDatabase()
      game.PopulateMap()


    case r: Received =>
      val parsed: JsValue = Json.parse(r.data.utf8String)

      println("SocketServer: Message from Server Received " + parsed)

      val Id = (parsed \ "userID").as[String]

      val action = (parsed \ "action").as[String]

      val username = (parsed \ "username").as[String]

      val ClientActor = context.actorOf(Props(classOf[PlayerActor], Id))

      if (action == "connected"){
        this.clients = this.clients + (Id -> ClientActor)
        ClientActor ! Register(self)
        println("SocketServer: User "+ Id + " Has Connected")
        game.AddUser(username, Id)
      }
      if (action == "disconnected"){
        this.clients = this.clients.filterKeys(_ != Id)
        game.RemovePlayer(Id)
      }
      if (action == "update"){
        val message = game.toJson()
        this.server ! Write(ByteString(message + "|"))
      }
      if (action == "moved"){
        val x = (parsed \ "x").as[Int] * 10
        val y = (parsed\ "y").as[Int] * 10

        game.PlayerMoved(Id, x, y)
      }

    case Update =>
      val message = game.toJson()
      this.server ! Write(ByteString(message + "|"))
  }
}

object SocketServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher

    import scala.concurrent.duration._

    val server = actorSystem.actorOf(Props(classOf[SocketServer]))

    actorSystem.scheduler.schedule(32.milliseconds, 32.milliseconds, server, Update)
  }
}
