package controllers

import play.api.libs.iteratee.Enumerator
import play.api.mvc._

class SampleController extends Controller {

  def sample1 = Action {
    Ok(views.html.index("SampleController#sample1"))
  }

  def sample2 = Action {
    Result(
      ResponseHeader(200, Map(CONTENT_TYPE -> "text/html")),
      Enumerator("Sample2".getBytes)
    )
  }

  def sample3 = Action {
    Redirect(routes.SampleController.sample2)
  }

  def sample4 = TODO

  def sample5(id: Long) = Action {
    Ok(views.html.index(s"id: $id"))
  }

  def sample6(id: Long) = Action {
    Ok(views.html.index(s"id: $id"))
  }

  def sample7(path: String) = Action {
    Ok(views.html.index(s"path: $path"))
  }

  def sample8(id: Long) = Action {
    Ok(views.html.index(s"id: $id"))
  }

  def sample9(fixedValue: String) = Action {
    Ok(views.html.index(s"fixedValue: $fixedValue"))
  }

  def sample10(defaultValue: Int) = Action {
    Ok(views.html.index(s"defaultValue: $defaultValue"))
  }

  def sample11(optValue: Option[String]) = Action {
    Ok(views.html.index(s"optValue: $optValue"))
  }
}
