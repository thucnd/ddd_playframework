package controllers.member

import controllers.BaseController
import json.JsonResult
import play.api.libs.json.Json
import util.Secured

/**
 * Created by dell5460 on 9/18/2015.
 */
class MemberController extends BaseController with Secured {

  def currentMember() = IsAuthenticated { _ => implicit request =>
    Ok(JsonResult.toSuccessJson(Some(
      Json.toJson(Map(
        "name" -> request.session.get("name").getOrElse(""),
        "email" -> request.session.get("email").getOrElse("")
      )))
    ))
  }

}
