package Gui

import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.paint.Color

class StartButton extends EventHandler[ActionEvent] {
  var plyname = "default" //+ id
  override def handle(event: ActionEvent): Unit = {
    Gui.graphics.children.removeAll(Gui.StartStack)
    Gui.AddUser(Gui.inputName.getText)
  }
}

class SkinActionRed extends EventHandler[ActionEvent] {
  val skin = Color.Red
  override def handle(event: ActionEvent): Unit = {
    Gui.Skin = skin
  }
}

class SkinActionBlue extends EventHandler[ActionEvent] {
  val skin = Color.Blue
  override def handle(event: ActionEvent): Unit = {
    Gui.Skin = skin
  }
}

class SkinActionGreen extends EventHandler[ActionEvent] {
  val skin = Color.Green
  override def handle(event: ActionEvent): Unit = {
    Gui.Skin = skin
  }
}
