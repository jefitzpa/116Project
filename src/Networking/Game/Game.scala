package Networking.Game

import Networking._

class Game {

  def CoinFound(plyId: String, CoinId: Int): Unit = {

  }

  //var usersOnline = new ListBuffer[Player]()
  //var coins = new ListBuffer[Coin]

  def AddUser(username: String, id: String): Player = {
    val location = List(400,300)
    var userName = username

    if (userName == ""){
      userName = "user" + id
    }

    val user = new Player(userName, location, id, 0)
    Database.AddPlayer(user)
    user
  }

  def RemovePlayer(id: String): Unit = {
    Database.RemovePlayer(id)
  }

  def PopulateMap(): Unit = {
    val r = scala.util.Random

    //code to randomize coin locations

    val location: List[Int] = List(r.nextInt(750), r.nextInt(550))
    for (coin <- 0 to 100){
      Database.AddCoin(new Coin(location, coin.toString))
    }
  }


}
