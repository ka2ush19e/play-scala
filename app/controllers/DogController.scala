package controllers

import javax.inject.Inject

import scala.concurrent.Future

import dao.DogDAO
import models.Dog
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

object DogController {
  case class DogForm(id: Option[Int], name: String, favoriteFood: String)

  val dogForm = Form(
    mapping(
      "id" -> optional(number),
      "name" -> nonEmptyText,
      "favorite_food" -> nonEmptyText
    )(DogForm.apply)(DogForm.unapply)
  )
}

class DogController @Inject()(val dogDao: DogDAO, val messagesApi: MessagesApi)
  extends Controller with I18nSupport {

  import DogController._

  def list = Action.async { implicit request =>
    dogDao.list().map(d => Ok(views.html.dog.list(d)))
  }

  def entry(idOpt: Option[Int]) = Action.async { implicit request =>
    idOpt match {
      case Some(id) =>
        dogDao.get(id).map { dog =>
          val filled = dogForm.fill(DogForm(dog.id, dog.name, dog.favoriteFood))
          Ok(views.html.dog.entry(filled))
        }
      case None => Future(Ok(views.html.dog.entry(dogForm)))
    }
  }

  def create() = Action.async { implicit request =>
    dogForm.bindFromRequest.fold(
      errors =>
        Future {
          println(errors)
          BadRequest(views.html.dog.entry(errors))
        },
      form =>
        dogDao.insert(Dog(None, form.name, form.favoriteFood)).map { _ =>
          Redirect(routes.DogController.list)
        }
    )
  }

  def update() = Action.async { implicit request =>
    dogForm.bindFromRequest.fold(
      errors =>
        Future {
          println(errors)
          BadRequest(views.html.dog.entry(errors))
        },
      form =>
        dogDao.update(Dog(form.id, form.name, form.favoriteFood)).map { _ =>
          Redirect(routes.DogController.list)
        }
    )
  }

  def delete(id: Int) = Action.async { implicit request =>
    dogDao.delete(id).map { _ =>
      Redirect(routes.DogController.list)
    }
  }
}
