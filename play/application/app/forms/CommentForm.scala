package forms

import play.api.data.Form
import play.api.data.Forms._

/**
 * Created by dell5460 on 7/30/2015.
 */
case class CommentForm(body: String)
object CommentForm {
  def form: Form[CommentForm] = Form(
    mapping(
      "body" -> text(
        maxLength = 500,
        minLength = 1
      )
    )(CommentForm.apply)(CommentForm.unapply)
  )
}
