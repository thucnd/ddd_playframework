package domain.model.comment

import java.text.SimpleDateFormat
import java.util.{Date, UUID}

import domain.model.member.MemberId


/**
 * Created by dell5460 on 7/23/2015.
 */
trait Comment {
  val id: CommentId

  val memberId: MemberId

  val name: String

  val email: String

  val body: String

  val created: String

  override def equals(other: Any): Boolean = other match {
    case that: Comment => that.id == this.id
    case _ => false
  }

  override def hashCode = 31 * id.##
}

// Factory Method
object Comment {

  // For edit information
  def apply(id: CommentId,
            memberId: MemberId,
            name: String,
            email: String,
            body: String,
            created: String): Comment = CommentImpl(
    id = id,
    memberId = memberId,
    name = name,
    email = email,
    body = body,
    created = created)

  // For create new Factory
  // Name Should be def create() or def apply()
  def create(memberId: String,
            name: String,
            email: String,
            body: String): Comment =
    CommentImpl(id = CommentId(UUID.randomUUID().toString),
      memberId = MemberId(memberId),
      name = name,
      email = email,
      body = body,
      created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date))
}

