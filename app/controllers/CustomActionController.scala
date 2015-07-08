package controllers

import scala.concurrent.Future

import play.api._
import play.api.mvc._

object LoggingAction extends ActionBuilder[Request] {
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    Logger.info(s"Headers: ${request.headers}")
    block(request)
  }
}

case class Logging[A](action: Action[A]) extends Action[A] {
  override def apply(request: Request[A]): Future[Result] = {
    Logger.info("Calling action")
    action(request)
  }
  lazy val parser = action.parser
}

class CustomActionController extends Controller{

  def sample1 = LoggingAction {
    Ok("Hello, World")
  }

  def sample2 = Logging {
    Action {
      Ok("Hello, World")
    }
  }
}
