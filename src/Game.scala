import UserFiles.Player

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

object Game {

  var usersOnline = new ListBuffer[Player]()

  def AddUser(username: String): Unit = {
    val location = findSuitableSpawn()
    val id = FindID()

    val user = new Player(username, location, id, 0)
    this.usersOnline += user
  }

  def findSuitableSpawn(): List[Int] = {
    List(0,0)
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

  def main(args: Array[String]): Unit = {
    val user1: Player = new Player("Jake", List(0,0), 0, 0)
    val user2: Player = new Player("Jake", List(0,0), 1, 0)
    val user3: Player = new Player("Jake", List(0,0), 2, 0)
    val user4: Player = new Player("Jake", List(0,0), 3, 0)

    this.usersOnline = ListBuffer(user1, user2, user3, user4)
  }

}
