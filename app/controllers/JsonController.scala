package controllers

import play.api.libs.json.{JsObject, JsString, Json}
import play.api.mvc._

class JsonController extends Controller {

  def sample1 = Action {
    Ok(Json.toJson(Map("status" -> "success1")))
  }

  def sample2 = Action {
    Ok(JsObject("status" -> JsString("success2") :: Nil))
  }
}
