package Testing

import Networking.Game.Game
import org.scalatest._

class UserTesting extends FunSuite{

  test("AddUser"){
    val game = new Game

    game.AddUser("Water")
    game.AddUser("1")
    game.AddUser("")

    //testing ids


    //testing names
  }

  test("CheckIDs"){
    val game = new Game

    //checking with no users online
    assert(game.FindID() == 0)

    //checking for no repeats
    for (num <- 0 to 100){
      game.AddUser("")
    }

  }

  test("spawnLocation"){
    val game = new Game

    //checking to make sure no player spawns over a 10x10 square from center
    for (num <- 0 to 100){
      game.AddUser("")
    }

  }

  test("removePlayer"){
    val game = new Game

    game.AddUser("user")
    game.AddUser("ihop")

    game.RemovePlayer(0)

  }

}
