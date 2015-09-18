package infrastructure.mysql

import anorm._
import play.api.Play.current
import play.api.db.DB

import scala.util.Try

/**
 * Created by dell5460 on 7/23/2015.
 */
class MemberSqlProvider extends SqlProvider {

  /**
   * Get Member information by Email
   * @param email
   * @return
   */
  def selectByEmail(email: String): Option[Map[String, String]] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          SELECT  *
          FROM    members
          WHERE   email    = {email}
        """
      ).on(
          'email -> email
        ).singleOpt().map { row =>
        Map(
          "member_id" -> row[String]("members.member_id"),
          "name" -> row[String]("members.name"),
          "email" -> row[String]("members.email"),
          "password" -> row[String]("members.password")
        )
      }
    }
  }

  /**
   * Get Member Detail By ID
   * @param id
   * @return
   */
  def selectById(id: String): Option[Map[String, String]] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          SELECT  *
          FROM    members
          WHERE   member_id    = {member_id}
        """
      ).on(
          'member_id -> id
        ).singleOpt().map { row =>
        Map(
          "member_id" -> row[String]("members.member_id"),
          "name" -> row[String]("members.name"),
          "email" -> row[String]("members.email"),
          "password" -> row[String]("members.password")
        )
      }
    }
  }

  /**
   * Create new Member
   * @param member
   * @return
   */
  override def insert(member: Map[String, String]): Try[Int] = Try {
    DB.withConnection { implicit connection =>
      SQL(
        """
            INSERT INTO members (member_id,   name,    email,    password,   modified, created)
            VALUES              ({member_id}, {name},  {email},  {password}, NOW(),    NOW())
        """
      ).on(
          'member_id -> member("member_id"),
          'name -> member("name"),
          'email -> member("email"),
          'password -> member("password")
        ).executeUpdate()
    }
  }
}
