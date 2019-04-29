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
    statement.execute("CREATE TABLE IF NOT EXISTS coins1 (id TEXT, XLocation INT, YLocation INT)")
  }

  def SetUpPlayerTable(): Unit = {
    val statement = connection.createStatement()
    statement.execute("CREATE TABLE IF NOT EXISTS players1 (username TEXT, id TEXT, coins INT, XLocation INT, YLocation INT)")
  }

  def AddPlayer(user: Player): Unit = {
    val statement = connection.prepareStatement("INSERT INTO players1 VALUES (?, ?, ?, ?, ?)")

    statement.setString(1, user.username)
    statement.setInt(3, user.Coins)
    statement.setInt(4, user.location.head)
    statement.setInt(5, user.location.tail.head)
    statement.setString(2, user.userId)

    statement.execute()
  }

  def AddCoin(coin: Coin): Unit = {
    val statement = connection.prepareStatement("INSERT INTO coins1 VALUE (?, ?, ?)")

    statement.setString(1, coin.id)
    statement.setInt(2, coin.location.head)
    statement.setInt(3, coin.location.tail.head)

    statement.execute()
  }

  def RemoveCoin(coinID: Int): Unit = {
    val statement = connection.prepareStatement("DELETE FROM coins1 WHERE id=?")
    statement.setInt(1, coinID)
    statement.execute()
  }

  def RemovePlayer(playerID: String): Unit = {
    val statement = connection.prepareStatement("DELETE FROM players1 WHERE id=?")
    statement.setString(1, playerID)
    statement.execute()
  }

  def UpdatePlayer(player: Player): Unit = {
    RemovePlayer(player.userId)
    AddPlayer(player)
  }

  def FindID(id: String): Boolean = {
    val statement = connection.prepareStatement("SELECT * FROM players1 WHERE id=?")

    statement.setString(1, id)
    val result = statement.executeQuery()

    result.first()
  }

  def CoinFindID(id: String): Boolean = {
    val statement = connection.prepareStatement("SELECT * FROM coins1 WHERE id=?")

    statement.setString(1, id)
    val result = statement.executeQuery()

    result.first()
  }
}
