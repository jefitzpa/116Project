package Server

import cask._


object ProjectServer extends cask.MainRoutes{

  @cask.get("/")
  def index() = "WebGui/Index.html"

  @cask.staticFiles("/WebGui/Gui.js")
  def staticFileRoutes() = "Server/resources/cask"

  @cask.post("/output")
  def newPlayer(request: cask.Request) = {
    new String(request.readAllBytes()).reverse
  }

  initialize()
}
