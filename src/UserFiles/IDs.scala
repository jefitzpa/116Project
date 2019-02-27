package UserFiles

object IDs {

  def AddUser(username: String): Unit = {
    val location = findSuitableSpawn()
    val id = CheckForID()

    val user = new Player(username, location, id, 0)
  }

  def findSuitableSpawn(): List[Int] = {

  }

  def CheckForID(): Int = {

  }


  def main(args: Array[String]): Unit = {
  }

}
