package Gui

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.scene.control.Button
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.text.Text
import play.api.libs.json.{JsValue, Json}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.TextField
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.{Group, Scene}

class HandleMessagesFromPython(gameState: GameState) extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    val message = Json.parse(objects.apply(0).toString)

    gameState.gs = message
  }
}

class GameState(var gs: JsValue)

object Gui extends JFXApp {

  var playersOnline: Map[String, Map[String, String]] = Map()
  var Coins: Map[String, Map[String, String]] = Map()

  var connected = false

  var graphics : Group = new Group {}

  val windowWidth : Double = 500
  val windowHeight : Double = 500

  var socket: Socket = IO.socket("http://localhost:8080/")

  val gameState = new GameState(Json.toJsObject(Map("players" -> playersOnline, "coins"-> Coins)))

  socket.on("gameState", new HandleMessagesFromPython(gameState))

  socket.connect()

  def Connect(name: String): Unit = {
    socket.emit("register", name, socket.id())
    this.connected = true
  }

  def UpdateGame(coins: Map[String, Map[String, String]], players: Map[String, Map[String, String]]): Unit = {

    Coins = coins
    playersOnline = players

    graphics.children.clear()

    if (connected) {
      amountOfCoins.setText("Coins: " + playersOnline(socket.id())("coins"))
    }
    graphics.children.addAll(amountOfCoins)

    for (player <- playersOnline.keys){
      createPlayer(playersOnline(player))
    }

    for (coin <- Coins.keys){
      createCoin(Coins(coin))
    }
  }

  def createPlayer(player: Map[String, String]): Unit = {
    val name = player("username")
    val location = List(player("x"), player("y"))

    val ply: Rectangle = new Rectangle{
      x = location.head.toDouble
      y = location.tail.head.toDouble
      fill = Skin
      width = 18
      height = 18
    }
    val plyName: Text = new Text(location.head.toDouble, location.tail.head.toDouble - 10, name)

    graphics.children.addAll(ply, plyName)
  }

  def createCoin(coin9: Map[String, String]): Unit = {
    val coin = new Rectangle(){
      x = coin9("x").toDouble
      y = coin9("y").toDouble
      fill = Color.Yellow
      width = 10
      height = 10
    }

    graphics.children.addAll(coin)
  }

  var keyStates: Map[String, Boolean] = Map("w" -> false, "a" -> false, "s" -> false, "d" -> false)

  def MakeKeyRequest(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => keyStates = Map("w" -> true, "a" -> false, "s" -> false, "d" -> false)
      case "A" => keyStates = Map("w" -> false, "a" -> true, "s" -> false, "d" -> false)
      case "S" => keyStates = Map("w" -> false, "a" -> false, "s" -> true, "d" -> false)
      case "D" => keyStates = Map("w" -> false, "a" -> false, "s" -> false, "d" -> true)
      case _ => println("Not an error")
    }
    socket.emit("keyStates", Json.toJson(keyStates))
  }

  var Skin : Color = Color.Red

  //start button and name textfield
  val start = new Button {
    setText("Start")
    setOnAction(new StartButton)
    setPrefSize(500, 100)
  }

  val inputName = new TextField
  inputName.setTranslateY(-80)

  //groups start button and textField
  val StartStack = new StackPane {
    translateX = 150
    translateY = 300
  }

  val amountOfCoins = new Text {
    setText("Coins: " + "0")
    setTranslateX(385)
    setTranslateY(25)
    setStyle("-fx-font: 30 ariel;")
  }
  StartStack.getChildren.addAll(start, inputName)

  graphics.children.addAll(amountOfCoins, StartStack)

  this.stage = new PrimaryStage {
    this.title = "Coin Collector"
    scene = new Scene(windowWidth, windowHeight + 10) {
      content = List(graphics)
      fill = Color.rgb(164, 166, 168)

      //controlPlayerMovement
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => MakeKeyRequest(event.getCode))
    }


    val update: Long => Unit = (time: Long) => {
      if (connected == true) {
        UpdateGame((gameState.gs \ "coins").as[Map[String, Map[String, String]]],
          (gameState.gs \ "players").as[Map[String, Map[String, String]]])
      }
    }
    AnimationTimer(update).start()
  }
}
