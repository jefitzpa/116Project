package Game

import Networking._

class Game {

  def CoinFound(plyId: Int, CoinId: Int): Unit = {

  }


  //var usersOnline = new ListBuffer[Player]()
  //var coins = new ListBuffer[Coin]

  def AddUser(username: String): Unit = {
    val location = findSuitableSpawn()
    val id = FindID()
    var userName = username

    if (userName == ""){
      userName = "user" + id.toString
    }

    val user = new Player(userName, location, id, 0)
    Database.AddPlayer(user)
  }

  def findSuitableSpawn(): List[Int] = {
    val r = scala.util.Random
    var spawn: List[Int] = List(250,250) //should be changed to the center of map
    var playerLocations: ListBuffer[List[Int]] = ListBuffer()
    if (this.usersOnline.nonEmpty){
      for (user <- this.usersOnline){
        playerLocations += user.location
      }
      //need to finalize size of map
      for (location <- playerLocations){
        if (location == spawn){
          spawn = List(250 + (r.nextDouble()*10 - 5).toInt , 250 + (r.nextDouble()*10 - 5).toInt)
        }
      }
    }
    spawn
  }

  def FindID(): Int = {
    var id: Int = 0
    var ids: ListBuffer[Int] = ListBuffer()
    if (this.usersOnline.nonEmpty) {
      for (user <- this.usersOnline) {
        ids += user.userId
      }
      id = ids.toList.last + 1
    }
    id
  }

  def RemovePlayer(id: Int): Unit = {
    for (user <- this.usersOnline){
      if (id == user.userId){
        this.usersOnline -= user
      }
    }
  }

  def PopulateMap(): Unit = {
    var x: Int = 0
    var y : Int = 0

    //code to randomize coin locations

    val location: List[Int] = List(x,y)
    for (coin <- 0 to 2500){
      coins += new Coin(location)
    }
  }


}
