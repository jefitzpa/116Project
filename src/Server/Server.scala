package Server

import javax.ws.rs.{GET, Path, Produces}

@Path("/helloworld")
class Server {
  @GET
  @Produces(Array("text/plain"))
  def getMessage:String = "Hello, World"
}