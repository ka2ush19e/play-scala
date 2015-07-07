package controllers

import models._
import play.api.Play.current
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages.Implicits._
import play.api.mvc._

class DogController extends Controller {

  def list = Action { implicit request =>
    val dogs = List[Dog](Dog(1, "Alice", "Rice"), Dog(2, "Bob", "Meet"))
    Ok(views.html.dog.list(dogs))
  }
}
