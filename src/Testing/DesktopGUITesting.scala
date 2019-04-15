package Testing

import Gui._
import org.scalatest.FunSuite
import scalafx.scene.paint.Color

class DesktopGUITesting extends FunSuite{
  test("Color Picker"){

    val GreenButton = new SkinActionGreen
    val RedButton = new SkinActionRed
    val BlueButton = new SkinActionBlue


    assert(RedButton.skin == Color.Red)
    assert(GreenButton.skin == Color.Green)
    assert(BlueButton.skin == Color.Blue)
  }
  test("Start Button") {

    //with no input
    val StartAction = new StartButton

    assert(StartAction.plyname == "default")

    //simulating an input
    StartAction.plyname = "dadBod"

    assert(StartAction.plyname == "dadBod")
  }

  test("StartButton (With No Name inputted)"){
    val StartAction = new StartButton

    assert(StartAction.plyname == "default")
  }

  test("No color selected (Reverts to default of red)"){
    val RedButton = new SkinActionRed

    assert(RedButton.skin == Color.Red)
  }
}
