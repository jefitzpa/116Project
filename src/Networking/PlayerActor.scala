package Networking

import Networking.Game.Game
import akka.actor.Actor

case class UpdateOtherPlayers(UserToLocation: Map[String, List[Int]])

case class AddNewCoin(location: List[Int])

case class CoinCollected(plyId: String, CoinId: Int)

case class Move(Displacement: List[Int])

case object Connect

case class Disconnect(Id : String)

class PlayerActor(Username: String) extends Actor{

  val game  = new Game

  override def receive: Receive = {

    case d: Disconnect => game.RemovePlayer(d.Id)

    case Connect => game.AddUser(Username, "0") //rewrite AddUser to include a database call

    case m: Move => //Need to figure out a way to reference each user, probably with database or within Networking.Game

    case cc: CoinCollected => game.CoinFound(cc.plyId, cc.CoinId) //need to write this function

    case anc: AddNewCoin => //Must find the way to send this information to the user

    case uop: UpdateOtherPlayers => //find a way to send information to the user and update that players screen

  }

}
