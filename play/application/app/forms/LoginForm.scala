package forms

/**
 * Created by dell5460 on 8/31/2015.
 */
import play.api.data.Form
import play.api.data.Forms._
case class LoginForm(email: String, password: String)
object LoginForm{
  def form: Form[LoginForm] = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(minLength = 8, maxLength = 16)
    )(LoginForm.apply)(LoginForm.unapply)
  )
}
