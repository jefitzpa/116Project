package UserFiles

class Player(val username: String, val location: List[Int], val userId: Int, var Coins: Int) {

  def move(key: String): Unit = {

  }

  def foundCoin(): Unit = {
    this.Coins += 1
  }


}
