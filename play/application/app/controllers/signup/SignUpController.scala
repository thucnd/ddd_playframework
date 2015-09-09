package controllers.signup

import javax.inject.Inject

import forms.MemberForm
import json.JsonResult
import play.api.mvc.{Action, Controller}
import services.signup.SignUpService

import scala.util.{Failure, Success}

/**
 * Created by dell5460 on 9/1/2015.
 */
class SignUpController @Inject()(signUpService: SignUpService) extends Controller {

  // Register new member
  def register() = Action { implicit request =>
    MemberForm.formMember.bindFromRequest.fold(
      formWithErrors => Ok(JsonResult.toErrorJson("User Input incorrect")),
      form => signUpService.createMember(form.name, form.email, form.password) match {
        case Success(member) => Ok(JsonResult.toSuccessJson())
        case Failure(e) => Ok(JsonResult.toErrorJson(e.getMessage))
      }
    )
  }
}
