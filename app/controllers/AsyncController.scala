package controllers

import java.io.{FileInputStream, File}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.concurrent.Promise
import play.api.libs.iteratee.Enumerator
import play.api.libs.ws.WS
import play.api.mvc._

class AsyncController extends Controller {

  def ws = Action.async {
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

  def timeout = Action.async {
    val sleepFuture = Future {
      Thread.sleep(1000)
      1
    }
    val timeoutFuture = Promise.timeout("Oops", 1 seconds)
    Future.firstCompletedOf(Seq(sleepFuture, timeoutFuture)).map {
      case i: Int => Ok("Got result: " + i)
      case s: String => InternalServerError(s)
    }
  }

  def download = Action {
    val file = new File("./activator-launch-1.3.5.jar")
    val fileContent = Enumerator.fromFile(file)
    Result(
      header = ResponseHeader(200, Map(CONTENT_LENGTH -> file.length.toString)),
      body = fileContent
    )
  }

  def sendFile1 = Action {
    Ok.sendFile(
      content = new File("./activator-launch-1.3.5.jar"),
      fileName = _ => "sendFile1.jar"
    )
  }

  def sendFile2 = Action {
    Ok.sendFile(
      content = new File("./activator-launch-1.3.5.jar"),
      inline = true
    )
  }

  def sendFile3 = Action {
    val data = new FileInputStream(new File("./activator-launch-1.3.5.jar"))
    val dataContent: Enumerator[Array[Byte]] = Enumerator.fromStream(data)
    Ok.chunked(dataContent)
  }
}
