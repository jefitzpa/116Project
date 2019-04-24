package Testing

import Networking.Database
import Networking.Game._
import org.scalatest.FunSuite

class DatabaseTesting extends FunSuite{
  test("SetupDataBase"){
    //ensure both tables are created
    Database.SetupDatabase()

    //check for both tables
    val statement1 = Database.connection.prepareStatement("select 1 from information_schema.tables where table_name = 'players'")
    val statement2 = Database.connection.prepareStatement("select 1 from information_schema.tables where table_name = 'coins'")

    assert(statement1.execute())
    assert(statement2.execute())
  }

  test("Player Inserted Correctly"){
    Database.SetupDatabase()

    val player = new Player("Jake", List(0,0), "12", 9)

    Database.AddPlayer(player)

    assert((Database.FindID("12")) == true)
  }

  test("Coin Inserted Correctly"){
    Database.SetupDatabase()

    val coin = new Coin(List(0,0), "637")

    Database.AddCoin(coin)

    assert(Database.CoinFindID("637") == true)
  }

  test("Coin Removed Successfully"){
    Database.SetupDatabase()

    val coin = new Coin(List(0,0), "637")

    Database.AddCoin(coin)

    Database.RemoveCoin(637)

    assert(Database.CoinFindID("637") == false)
  }
}
