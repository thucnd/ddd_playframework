package domain.model.member

/**
 * Created by dell5460 on 8/28/2015.
 */

private[member] case class MemberImpl(id: MemberId,
                                      name: String,
                                      email: String,
                                      password: String) extends Member {
  def compare(that: Member) =
    id.value.compare(that.id.value)
}
