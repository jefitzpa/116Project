package Testing

import Networking.Game.Game
import org.scalatest._

class UserTesting extends FunSuite{

  test("AddUser"){
    val game = new Game

    game.AddUser("Water","0")
    game.AddUser("1", "1")
    game.AddUser("", "2")
  }

  test("spawnLocation"){
    val game = new Game

    //checking to make sure no player spawns over a 10x10 square from center

  }

  test("removePlayer"){
    val game = new Game

    game.AddUser("user", "0")
    game.AddUser("ihop","2")

    game.RemovePlayer("0")

  }

}
