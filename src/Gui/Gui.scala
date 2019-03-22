package Gui

import Game.Game
import javafx.scene.control.Button
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.text.Text
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.TextField
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.scene.{Group, Scene}

object Gui extends JFXApp {

  val game =  new Game

  var coins = 0

  val windowWidth : Double = 800
  val windowHeight : Double = 600

  var Skin : Color = Color.Red

  var graphics : Group = new Group {}

  //needs to make request to server
  def AddUser(name: String): Unit = {
    playerName.setText(name)
    graphics.children.addAll(PlayerStack)
    graphics.children.removeAll(StartStack)
  }

  //initializes player graphic
  val player: Rectangle = new Rectangle {
    width = 18
    height = 18
    fill = Skin
  }

  //Create player label
  val playerName = new Text
  playerName.setTranslateY(-20)

  //group player name and player
  val PlayerStack = new StackPane {
    translateX = 400
    translateY = 300
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
  StartStack.getChildren.addAll(start, inputName)

  graphics.children.addAll(amountOfCoins, coin, StartStack)

  def MakeKeyRequest(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => PlayerStack.translateY.value -= 10
      case "A" => PlayerStack.translateX.value -= 10
      case "S" => PlayerStack.translateY.value += 10
      case "D" => PlayerStack.translateX.value += 10
      case "Enter" => new StartButton
      case _ => println(keyCode.getName + " pressed with no action")
    }
  }

  def placeNewCoin(): Unit ={
    coin.setCenterX(210)
    coin.setCenterY(300)
    graphics.children.addAll(coin)
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
     if (coin.centerX.value == PlayerStack.translateX.value & coin.centerY.value == PlayerStack.translateY.value){
       coins += 1
       println(coin.centerX.value, PlayerStack.translateX.value)
       graphics.children.removeAll(coin)
       placeNewCoin()
     }
     amountOfCoins.setText("Coins: " + coins.toString)
    }
    AnimationTimer(update).start()
  }
}
