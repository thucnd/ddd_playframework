package json.member

import domain.model.member.Member
import play.api.libs.json.{JsValue, Json}

/**
 * Created by dell5460 on 7/30/2015.
 */
case class MemberJson(id: String,
                      email: String,
                      name: String)

object MemberJson {

  def write(m: Member): JsValue = Json.obj(
    "id" -> m.id.value,
    "email" -> m.email,
    "name" -> m.name
  )
}
