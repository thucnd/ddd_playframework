package controllers.login

import javax.inject.Inject

import controllers.BaseController
import forms.LoginForm
import json.JsonResult
import json.member.MemberJson
import play.api.Logger
import play.api.i18n.Messages
import play.api.mvc._
import services.login.LoginService
import util.Secured

import scala.util.{Failure, Success}

class LoginController @Inject()(loginService: LoginService) extends BaseController with Secured {

  // Authenticate Member
  def login = Action { implicit request =>
    // Log some debug info
    Logger.debug(Messages("exception.password.invalid"))
    Logger.error("exception.password.invalid")

    LoginForm.form.bindFromRequest.fold(
      errors => BadRequest,
      form => {
        loginService.authenticate(form.email, form.password) match {
          case Success(member) =>
            Ok(JsonResult.toSuccessJson(Some(MemberJson.write(member))))
              .withSession(
                "email" -> member.email,
                "name" -> member.name,
                "id" -> member.id.value
              )
          case Failure(e) => Ok(JsonResult.toErrorJson(e.getMessage))
        }
      }
    )
  }

  def logout = Action { implicit request =>
    Ok(JsonResult.toSuccessJson()).withNewSession
  }
}
