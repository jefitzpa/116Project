package Gui

import javafx.event.{ActionEvent, EventHandler}

class StartButton extends EventHandler[ActionEvent] {
  override def handle(event: ActionEvent): Unit = {
    Gui.AddUser(Gui.inputName.getText)
  }
}
