package domain.model.member

import java.util.UUID

trait Member {
  val id: MemberId

  val name: String

  val email: String

  val password: String

  override def equals(other: Any): Boolean = other match {
    case that: Member => that.id == this.id
    case _ => false
  }

  override def hashCode = 31 * id.##
}

// Factory Method
object Member {
  def apply(member_id: String,
            name: String,
            email: String,
            password: String): Member = MemberImpl(
    id = MemberId(member_id),
    name = name,
    email = email,
    password = password
  )

  // Name should be def create() or def apply()
  def create(name: String,
            email: String,
            password: String): Member = MemberImpl(
    id = MemberId(UUID.randomUUID().toString),
    name = name,
    email = email,
    password = password
  )
}

