package UserFiles

object IDs {

  def AddUser(username: String): Unit = {
    val location = findSuitableSpawn()
    val id = CheckForID()

    val user = new Player(username, location, id, 0)
    println(username)
    println(location)
    println(id)
  }

  def findSuitableSpawn(): List[Int] = {
    val r = scala.util.Random

    var x: Int = r.nextInt(100)
    var y: Int = r.nextInt(100)

    List(x,y)
  }

  def CheckForID(): Int = {
    0
  }


  def main(args: Array[String]): Unit = {
    AddUser("Jake")
  }

}
