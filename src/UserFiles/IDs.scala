package UserFiles

import java.io.{File, PrintWriter}

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks

object IDs {

  import scala.io.Source
  val writer = new PrintWriter(new File("src\\UserFiles\\USERS.txt"))

  def AddUser(user: Array[String]): Unit = {
    //user array in form (name, location)
    val lines: ListBuffer[String] = ListBuffer()
    val username = user(0)
    val location = user(1)
    var userId = ""
    val file = Source.fromFile("src\\UserFiles\\USERS.txt")
    val loop = new Breaks
    loop.breakable {
      for (id <- 0 to 9999) {
        if (CheckForID(id.toString) == "Empty ID") {
          userId = id.toString
          loop.break()
        }
      }
    }
    for (line <- file.getLines()) {
      if (line.split(",")(0) == userId) {
        lines += (userId,",", username,",", location, "\n")
        println(lines.toString())
      } else {
        lines += line
      }
    }
    for (line <- lines) {
      writer.write(line + "\n")
    }
  }


  def CheckForID(id: String): String = {
    val file = Source.fromFile("src\\UserFiles\\USERS.txt")
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
  }



  def main(args: Array[String]): Unit = {
    PopulateIDs()
  }

}
