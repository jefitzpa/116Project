package Testing

import Game.Game
import org.scalatest._

import scala.collection.mutable.ListBuffer

class UserTesting extends FunSuite{

  test("AddUser"){
    val game = new Game

    game.AddUser("Water")
    game.AddUser("1")
    game.AddUser("")

    //testing ids
    assert(game.usersOnline(0).userId == 0)
    assert(game.usersOnline(1).userId == 1)
    assert(game.usersOnline(2).userId == 2)

    //testing names
    assert(game.usersOnline(0).username == "Water")
    assert(game.usersOnline(1).username == "1")
    assert(game.usersOnline(2).username == "user2")
  }

  test("CheckIDs"){
    val game = new Game

    //checking with no users online
    assert(game.FindID() == 0)

    //checking for no repeats
    for (num <- 0 to 100){
      game.AddUser("")
    }
    var ids: ListBuffer[Int] = ListBuffer()
    for (user <- game.usersOnline){
      ids += user.userId
    }
    assert(ids.distinct.size == ids.size)
  }

  test("spawnLocation"){
    val game = new Game

    //checking to make sure no player spawns over a 10x10 square from center
    for (num <- 0 to 100){
      game.AddUser("")
    }
    for (user <- game.usersOnline){
      assert(user.location(0) <= 260 && user.location(1) >= 240)
      assert(user.location(1) <= 260 && user.location(1) >= 240)
    }
  }

  test("removePlayer"){
    val game = new Game

    game.AddUser("user")
    game.AddUser("ihop")

    game.RemovePlayer(0)

    assert(game.usersOnline(0).username == "ihop")
  }

}
