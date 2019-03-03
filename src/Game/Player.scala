package Game

class Player(var username: String, var location: List[Int], var userId: Int, var Coins: Int) {

  def move(displacement: List[Int]): Unit = {
    val x: Int = this.location.head + displacement.head
    val y: Int = this.location(1) + displacement(1)

    this.location = List(x,y)
  }

  def foundCoin(): Unit = {
    this.Coins += 1
  }


}
