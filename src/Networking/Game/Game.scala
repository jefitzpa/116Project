package Networking.Game

import Networking._

class Game {

  def CoinFound(plyId: Int, CoinId: Int): Unit = {

  }

  //var usersOnline = new ListBuffer[Player]()
  //var coins = new ListBuffer[Coin]

  def AddUser(username: String): Player = {
    val location = List(400,300)
    val id = FindID()
    var userName = username

    if (userName == ""){
      userName = "user" + id.toString
    }

    val user = new Player(userName, location, id, 0)
    Database.AddPlayer(user)
    user
  }

  def FindID(): Int = {
    val r = scala.util.Random
    var id: Int = r.nextInt()

    while (Database.FindID(id) || id <= 0){
      id = r.nextInt()
    }

    id
  }

  def RemovePlayer(id: Int): Unit = {
    Database.RemovePlayer(id)
  }

  def PopulateMap(): Unit = {
    val r = scala.util.Random

    //code to randomize coin locations

    val location: List[Int] = List(r.nextInt(750), r.nextInt(550))
    for (coin <- 0 to 100){
      Database.AddCoin(new Coin(location, coin))
    }
  }


}
