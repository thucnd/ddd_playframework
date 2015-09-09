package json

import play.api.libs.json.{JsBoolean, JsValue, Json}
import util.CommonConst

/**
 * Created by dell5460 on 7/30/2015.
 */
object JsonResult {

  def success(): JsValue = Json.toJson(Map("status" -> CommonConst.STATUS_SUCCESS))

  def fails(): JsValue = Json.toJson(Map("status" -> CommonConst.STATUS_FAILS))

  def error(msg: String): JsValue = Json.toJson(Map("error" -> msg))

  def status(status: Int): JsValue = Json.toJson(Map("status" -> status))

  def fromMap(result: Map[String, String]): JsValue = Json.toJson(result)

  def toSuccessJson(data: Option[JsValue] = None,
                    session: Option[JsValue] = None) = {
    var result = Json.obj()
    result +=("success", JsBoolean(true))

    if (data.isDefined) {
      result +=("data", data.get)
    }

    if (session.isDefined) {
      result +=("session", session.get)
    }

    result
  }

  def toErrorJson(message: String,
                  session: Option[JsValue] = None) = {
    var result = Json.obj()
    result +=("success", JsBoolean(false))

    result +=("message", Json.toJson(message))

    if (session.isDefined) {
      result +=("session", session.get)
    }

    result
  }

}
