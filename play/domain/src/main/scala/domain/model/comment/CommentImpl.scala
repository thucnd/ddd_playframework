package domain.model.comment

import domain.model.member.MemberId

/**
 * Created by dell5460 on 8/28/2015.
 */
private[comment] case class CommentImpl(id: CommentId,
                                        memberId: MemberId,
                                        name: String,
                                        email: String,
                                        body: String,
                                        created: String) extends Comment {
  def compare(that: Comment): Int =
    id.value.compare(that.id.value)
}
