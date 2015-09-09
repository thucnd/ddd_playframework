package domain.lifecycle.member

import domain.model.member.Member
import infrastructure.mysql.MemberSqlProvider

/**
 * Created by dell5460 on 7/23/2015.
 */
trait MemberRepository {

  // Get UserInformation By Email
  def findByEmail(email: String): Option[Member]
}

object MemberRepository extends MemberRepositoryOnDBImpl(new MemberSqlProvider)
