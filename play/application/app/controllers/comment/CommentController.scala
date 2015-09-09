package controllers.comment

import javax.inject.Inject

import forms.CommentForm
import json.JsonResult
import json.comment.CommentJson
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.Controller
import services.comment.CommentService
import util.Secured

import scala.util.{Failure, Success}

/**
 * Created by dell5460 on 9/1/2015.
 */
class CommentController @Inject()(commentService: CommentService) extends Controller with Secured {
  def list(page: Option[Int]) = IsAuthenticated { _ => implicit request =>
    val session = Json.toJson(Map(
      "name" -> request.session.get("name").getOrElse(""),
      "email" -> request.session.get("email").getOrElse("")
    ));

    commentService.commentListByPage(page.get) match {
      case Success(comments) =>
        Ok(JsonResult.toSuccessJson(Some(JsArray(
          comments.map(comment => CommentJson.write(comment))
        )),
        Some(session)
        ))
      case _ => Ok(JsonResult.toErrorJson("Not Found", Some(session)))
    }
  }

  /**
   * Create new Post
   * @return
   */
  def create() = IsAuthenticated { _ => implicit request =>
    CommentForm.form.bindFromRequest.fold(
      formWithErrors => Ok(JsonResult.toErrorJson("Comment Input incorrect")),
      form => commentService.createComment(form.body) match {
        case Success(comment) => Ok(JsonResult.toSuccessJson())
        case Failure(e) => Ok(JsonResult.toErrorJson(e.getMessage))
      }
    )
  }
}