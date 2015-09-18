package domain.lifecycle.member

import domain.lifecycle.AbstractRepositoryOnMysql
import domain.model.member.{Member, MemberId}
import infrastructure.mysql.{MemberSqlProvider, SqlProvider}

/**
 * Created by dell5460 on 7/23/2015.
 */
private[member] class MemberRepositoryOnDBImpl(memberSqlProvider: MemberSqlProvider)
  extends AbstractRepositoryOnMysql[Member, MemberId]
  with MemberRepository {

  // Get UserInformation By Email
  override def findByEmail(email: String): Option[Member] = {
    memberSqlProvider.selectByEmail(email).map(convertToEntity)
  }

  protected val modelName: String = "Member"

  protected val sqlProvider: SqlProvider = memberSqlProvider

  override protected def convertToMap(entity: Member): Map[String, String] = Map(
    "member_id" -> entity.id.value,
    "name" -> entity.name,
    "email" -> entity.email,
    "password" -> entity.password
  )

  override protected def convertToEntity(map: Map[String, String]): Member =
    Member(
      map("member_id"),
      map("name"),
      map("email"),
      map("password")
    )
}
