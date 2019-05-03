package Networking.Game

import Networking._
import play.api.libs.json.{JsValue, Json}

import scala.util.Random

class Game {

  var players: Set[Player] = Set()
  var coins: Set[Coin] = Set()

  def CoinFound(plyId: String, CoinId: Int): Unit = {

  }

  def AddUser(username: String, id: String): Player = {
    val location = List(400,300)
    var userName = username

    if (userName == ""){
      userName = "user" + id
    }

    val user = new Player(userName, location, id, 0)
    Database.AddPlayer(user)
    this.players += user
    user
  }

  def RemovePlayer(id: String): Unit = {
    Database.RemovePlayer(id)
    for (player <- players){
      if (player.userId == id){
        players -= player
      }
    }
  }

  def PopulateMap(): Unit = {
    val r = scala.util.Random

    for (coin <- 0 to 49){
      val location: List[Int] = List(r.nextInt(485), r.nextInt(485))
      Database.AddCoin(new Coin(location, coin.toString))
      this.coins += new Coin(location, coin.toString)
    }
  }

  def toJson(): JsValue = {
    PlayerFoundCoin()
    var playerMap: Map[String, Map[String, String]] = Map()
    for (player <- this.players){
      playerMap = playerMap + (player.userId -> Map("username" -> player.username,
        "coins" -> player.Coins.toString,
        "x" -> player.location.head.toString, "y" -> player.location.tail.head.toString))
    }

    var coinMap: Map[String, Map[String, String]] = Map()
    for (coin <- this.coins){
      coinMap += (coin.id -> Map("x" -> coin.location.head.toString,
      "y" -> coin.location.tail.head.toString))
    }

    val gs: Map[String, JsValue] = Map(
      "players" -> Json.toJson(playerMap),
      "coins" -> Json.toJson(coinMap)
    )
    Json.toJsObject(gs)
  }

  def PlayerMoved(id: String, x: Int, y: Int): Unit = {
    for (players <- players){
      if (players.userId == id){
        players.location = List(players.location.head + x, players.location.tail.head + y)
      }
    }
  }

  def PlayerFoundCoin(): Unit = {
    for (player <- players){
      for (coin <- coins){
        if (Math.sqrt(Math.pow(player.location.head - coin.location.head, 2) + Math.pow(player.location.tail.head -
        coin.location.tail.head, 2)) <= 15){
          player.foundCoin()
          coins -= coin
          Database.RemoveCoin(coin.id.toInt)

          val r = new Random()
          val location: List[Int] = List(r.nextInt(485), r.nextInt(485))
          Database.AddCoin(new Coin(location, coin.id))
          this.coins += new Coin(location, coin.id)
        }
        }
      }
    }
  }

