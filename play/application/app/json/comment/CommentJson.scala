package json.comment

import domain.model.comment.Comment
import play.api.libs.json.{JsValue, Json}

/**
 * Created by dell5460 on 7/30/2015.
 */
case class CommentJson(id: String,
                       name: String,
                       email: String,
                       body: String,
                       created: String)

object CommentJson {
  def apply(comment: Comment): CommentJson =
    CommentJson(
      comment.id.value,
      comment.name,
      comment.email,
      comment.body,
      comment.created
    )

  def write(o: Comment): JsValue = Json.obj(
    "id" -> o.id.value,
    "name" -> o.name,
    "email" -> o.email,
    "body" -> o.body,
    "created" -> o.created
  )
}
