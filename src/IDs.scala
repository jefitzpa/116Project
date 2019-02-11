object IDs {
  import scala.io.Source
  import java.io.PrintWriter

  def CheckForID(id: String): String = {
    val file = Source.fromFile("src\\USERS.txt")
    var user: String = "Empty ID"
    for (line <- file.getLines()){
        if (line.split(",")(0) == id){
          user = line.split(",")(1)
       }
    }
    file.close()
    user
  }

  def AddUser(input: Array[String]): Unit ={

  }

  def PopulateIDs(): Unit = {
    for (ids <- 0 to 9999){
      new PrintWriter("src\\USERS.txt") { write(ids.toString()+",,,\n"); close }
      //not working i need to use a different print method
    }


  }



  def main(args: Array[String]): Unit = {
    PopulateIDs()
  }

}
