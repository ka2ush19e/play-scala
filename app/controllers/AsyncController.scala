package controllers

import play.api.Play.current
import play.api.libs.ws.WS
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class AsyncController extends Controller {

  def sample1 = Action.async {
    val response = WS.url("http://www.example.com").get()
    calc()
    response.map { response =>
      Ok(response.body)
    }
  }

  private def calc() {
    1 to 5 foreach { i =>
      println(s"calc: $i")
      Thread.sleep(1000)
    }
  }
}
