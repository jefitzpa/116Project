package Server

import javax.ws.rs.{GET, Produces, Path}

@Path("/helloworld")
class Server {
  @GET
  @Produces(Array("text/plain"))
  def getMessage:String = "Hello, World"
}