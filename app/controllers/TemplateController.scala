package controllers

import play.api.mvc._

class TemplateController extends Controller {

  def show = Action {
    val list = List("Apple", "Orange", "Banana")
    Ok(views.html.show("Hello, PlayTemplate", list))
  }

}
