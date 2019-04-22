package Gui

import Networking.Game.Game
import javafx.scene.control.Button
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.text.Text
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.TextField
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.{Group, Scene}

object Gui extends JFXApp {

  val game =  new Game

  var coins = 0

  val windowWidth : Double = 800
  val windowHeight : Double = 600

  var Skin : Color = Color.Red

  var graphics : Group = new Group {}

  var playerXvelo = 0.0
  var playerYvelo = 0.0

  //Create player label
  val playerName = new Text
  playerName.setTranslateY(-20)

  //needs to make request to server
  def AddUser(name: String): Unit = {
    player.fill = Skin
    playerName.setText(name)
    graphics.children.addAll(PlayerStack)
  }

  //initializes player graphic
  val player: Circle = new Circle {
    radius = 10
    centerX= 400
    centerY = 300
    fill = Skin
  }

  //group player name and player
  val PlayerStack = new StackPane {
    translateX = player.centerX.value
    translateY = player.centerY.value
  }
  PlayerStack.getChildren.addAll(player, playerName)

  //simple coin, will need to be revised
  val coin = new Circle()
  coin.setCenterX(400)
  coin.setCenterY(100)
  coin.setRadius(9)
  coin.fill = Color.Yellow

  //start button and name textfield
  val start = new Button {
    setText("Start")
    setOnAction(new StartButton)
    setPrefSize(500, 100)
  }
  var SkinRed = new Button {
    setText("Red")
    setOnAction(new SkinActionRed)
    setPrefSize(100, 100)
    setTranslateY(120)
  }

  var SkinBlue = new Button {
    setText("Blue")
    setOnAction(new SkinActionBlue)
    setPrefSize(100, 100)
    setTranslateX(-200)
    setTranslateY(120)
  }

  var SkinGreen = new Button {
    setText("Green")
    setOnAction(new SkinActionGreen)
    setPrefSize(100, 100)
    setTranslateX(200)
    setTranslateY(120)
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
    setTranslateX(650)
    setTranslateY(25)
    setStyle("-fx-font: 30 ariel;")
  }
  StartStack.getChildren.addAll(start, inputName, SkinRed, SkinBlue, SkinGreen)

  graphics.children.addAll(amountOfCoins, coin, StartStack)

  def MakeKeyRequest(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => playerYvelo = -10
      case "A" => playerXvelo = -10
      case "S" => playerYvelo = 10
      case "D" => playerXvelo = 10
      case "Enter" => new StartButton
      case _ => println("Not an error")
    }
  }

  def placeNewCoin(): Unit ={
    val r = scala.util.Random
    coin.setCenterX(r.nextInt(750))
    coin.setCenterY(r.nextInt(550))
    graphics.children.removeAll(PlayerStack,amountOfCoins)
    graphics.children.addAll(coin, PlayerStack, amountOfCoins)
  }

  this.stage = new PrimaryStage {
    this.title = "Coin Collector"
    scene = new Scene(windowWidth, windowHeight) {
      content = List(graphics)
      fill = Color.rgb(164, 166, 168)

      //controlPlayerMovement
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => MakeKeyRequest(event.getCode))
    }


    val update: Long => Unit = (time: Long) => {
      PlayerStack.translateX.value += playerXvelo
      PlayerStack.translateY.value += playerYvelo

      playerYvelo = 0.0
      playerXvelo = 0.0

     if (Math.pow(player.centerX.value - coin.centerX.value,2) + Math.pow(player.centerY.value - coin.centerY.value, 2) < 361){
       coins += 1
       graphics.children.removeAll(coin)
       placeNewCoin()
     }
      player.setCenterX(PlayerStack.translateX.value + 10)
      player.setCenterY(PlayerStack.translateY.value + 10)
      amountOfCoins.setText("Coins: " + coins.toString)
    }
    AnimationTimer(update).start()
  }
}
