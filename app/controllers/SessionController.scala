package controllers

import java.util.Date

import play.api.mvc._

class SessionController extends Controller {

  def setSession = Action { implicit request =>
    Ok("Save session").withSession(request.session + ("date" -> new Date().toString))
  }

  def getSession = Action { implicit request =>
    request.session.get("date").map { date =>
      Ok(s"Accessed: $date")
    }.getOrElse {
      Ok(s"Never accessed")
    }
  }

  def removeSession = Action { implicit request =>
    Ok("Remove session").withNewSession
  }

  def getFlash = Action { implicit request =>
    Ok {
      request.flash.get("message").getOrElse("nothing")
    }
  }

  def setFlash = Action { implicit request =>
    Redirect(routes.SessionController.getFlash).flashing("message" -> "Save flash")
  }
}
