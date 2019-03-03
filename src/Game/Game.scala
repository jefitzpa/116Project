package Game

import UserFiles.Player

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

class Game {

  var usersOnline = new ListBuffer[Player]()

  def AddUser(username: String): Unit = {
    val location = findSuitableSpawn()
    val id = FindID()
    var userName = username

    if (userName == ""){
      userName = "user" + id.toString
    }

    val user = new Player(userName, location, id, 0)
    this.usersOnline += user
    //needs to add player to screen still
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
          spawn = List(250 + (r.nextInt(1)*10 -5) , 250 + (r.nextInt(1)*10 - 5))
        }
      }
    }
    spawn
  }

  def FindID(): Int = {
    val loop = new Breaks
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

}
