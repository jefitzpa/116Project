import scala.util.control.Breaks

object IDs {
  import java.io.{File, PrintWriter}

  import scala.io.Source
  val writer = new PrintWriter(new File("src\\USERS.txt"))

  def AddUser(user: Array[String]): Unit ={
    //user array in form (name, location)
    var username = user(0)
    var location = user(1)
    var userId = ""
    val file = Source.fromFile("src\\USERS.txt")
    val loop = new Breaks
    loop.breakable{
      for (id <- 0 to 9999){
        if (CheckForID(id.toString) == "Empty ID"){
          userId = id.toString
          loop.break()
        }
      }
    }
    //this loop doesn't quite work, i need a way to append the file rather than overwrite
    for (line <- file.getLines()) {
      if (line.split(",")(0) == userId) {
        writer.write(userId + "," + username + "," + location + "\n")
      }
      writer.write(line)
    }
    writer.close()
  }


  def CheckForID(id: String): String = {
    val file = Source.fromFile("src\\USERS.txt")
    var user: String = "Empty ID"
    for (line <- file.getLines()){
      if (line.split(",")(0) == id) {
        if (line.split(",").length > 1) {
          user = line.split(",")(1)
        }
      }
    }
    file.close()
    user
  }


  def PopulateIDs(): Unit = {
    for (ids <- 0 to 9999){
      writer.write(ids + "," + "," + "\n")
    }
    writer.close()
  }



  def main(args: Array[String]): Unit = {
    PopulateIDs()
    AddUser(Array("Jake", "0,0"))
  }

}
