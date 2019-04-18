package Testing

import Networking.Database
import org.scalatest.FunSuite

class DatabaseTesting extends FunSuite{
  test("SetupDataBase"){
    //ensure both tables are created
    Database.SetupDatabase()

    //check for both tables
    val statement1 = Database.connection.prepareStatement("if exists (select 1 from information_schema.tables where table_name = 'players')")
    val statement2 = Database.connection.prepareStatement("if exists (select 1 from information_schema.tables where table_name = 'coins')")

    assert(statement1.execute())
    assert(statement2.execute())
  }
}
