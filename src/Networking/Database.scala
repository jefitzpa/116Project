package Networking

import java.sql.{Connection, DriverManager}

import Networking.Game._

object Database {

  val url = "jdbc:mysql://localhost/mysql?serverTimezone=UTC"
  val username = "Admin" //make this more secure
  val password = "1234`"
  var connection: Connection = DriverManager.getConnection(url, username, password)


  def SetupDatabase(): Unit ={
    SetupCoinTable()
    SetUpPlayerTable()
  }

  def SetupCoinTable(): Unit = {
    val statement = connection.createStatement()
    statement.execute("CREATE TABLE IF NOT EXISTS coins (id INT, XLocation INT, YLocation INT)")
  }

  def SetUpPlayerTable(): Unit = {
    val statement = connection.createStatement()
    statement.execute("CREATE TABLE IF NOT EXISTS players (username TEXT, id INT, coins INT, XLocation INT, YLocation INT)")
  }

  def AddPlayer(user: Player): Unit = {
    val statement = connection.prepareStatement("INSERT INTO players VALUE (?, ?, ?, ?, ?)")

    statement.setString(1, user.username)
    statement.setInt(2, user.userId)
    statement.setInt(3, user.Coins)
    statement.setInt(4, user.location.head)
    statement.setInt(5, user.location.tail.head)

    statement.execute()
  }

  def AddCoin(coin: Coin): Unit = {
    val statement = connection.prepareStatement("INSERT INTO coins VALUE (?, ?, ?)")

    statement.setInt(1, coin.id)
    statement.setInt(2, coin.location.head)
    statement.setInt(3, coin.location.tail.head)

    statement.execute()
  }

  def RemoveCoin(coinID: Int): Unit = {
    val statement = connection.prepareStatement("DELETE FROM coins WHERE id=?")
    statement.setInt(1, coinID)
    statement.execute()
  }

  def RemovePlayer(playerID: Int): Unit = {
    val statement = connection.prepareStatement("DELETE FROM players WHERE id=?")
    statement.setInt(1, playerID)
    statement.execute()
  }

  def UpdatePlayer(player: Player): Unit = {
    RemovePlayer(player.userId)
    AddPlayer(player)
  }

  def FindID(id: Int): Boolean = {
    val statement = connection.prepareStatement("SELECT * FROM players WHERE id=?")

    statement.setInt(1, id)
    val result = statement.executeQuery()

    result.first()
  }

  def CoinFindID(id: Int): Boolean = {
    val statement = connection.prepareStatement("SELECT * FROM coins WHERE id=?")

    statement.setInt(1, id)
    val result = statement.executeQuery()

    result.first()
  }
}
