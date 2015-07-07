package controllers

import models._
import play.api.Play.current
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages.Implicits._
import play.api.mvc._

class UserController extends Controller {

  val userForm = Form(
    mapping(
      "name" -> nonEmptyText(),
      "email" -> email,
      "age" -> number(min = 0)
    )(User.apply)(User.unapply))

  def entryInit = Action {
    val filledForm = userForm.fill(User("user name", "email address", 24))
    Ok(views.html.user.entry(filledForm))
  }

  def entrySubmit = Action { implicit request =>
    userForm.bindFromRequest.fold(
      errors => {
        println("Error")
        BadRequest(views.html.user.entry(errors))
      },
      success => {
        println("Success")
        Ok(views.html.user.entrySubmit())
      }
    )
  }
}
