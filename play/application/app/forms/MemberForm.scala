package forms

import play.api.data.Form
import play.api.data.Forms._
import util.CommonConst

/**
 * Created by dell5460 on 7/30/2015.
 */
case class MemberForm(name: String,
                      email: String,
                      password: String)

object MemberForm {
  val formMember = Form(
    mapping(
      "name" -> text(
        maxLength = CommonConst.NAME_MAX_LENGTH
      ),
      "email" -> email,
      "password" -> text(
        minLength = CommonConst.PASSWORD_MIN_LENGTH,
        maxLength = CommonConst.PASSWORD_MAX_LENGTH
      )
    )(MemberForm.apply)(MemberForm.unapply)
  )
}
