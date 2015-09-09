package controllers.login

import javax.inject.Inject

import forms.LoginForm
import json.JsonResult
import json.member.MemberJson
import play.api.mvc._
import services.login.LoginService
import util.Secured

import scala.util.{Failure, Success}

class LoginController @Inject()(loginService: LoginService) extends Controller with Secured {

  // Authenticate Member
  def login = Action { implicit request =>
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
