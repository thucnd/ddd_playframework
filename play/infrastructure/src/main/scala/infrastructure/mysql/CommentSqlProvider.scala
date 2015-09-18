package infrastructure.mysql

import anorm._
import org.joda.time.DateTime
import play.api.Play.current
import play.api.db.DB

import scala.util.Try

/**
 * Created by dell5460 on 7/23/2015.
 */
class CommentSqlProvider extends SqlProvider {

  /**
   * Get Comment List By offset, limit
   * @param offset
   * @param limit
   * @return
   */
  def selectByOffset(offset: Int, limit: Int): List[Map[String, String]] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          SELECT      comments.comment_id,
                      comments.member_id,
                      comments.created,
                      comments.body,
                      members.name,
                      members.email
          FROM        comments
          INNER JOIN  members
          ON          members.member_id = comments.member_id
          ORDER BY    comments.modified
          DESC
          LIMIT       {offset},{max_comment}
        """)
        .on(
          'max_comment -> limit,
          'offset -> (offset - 1) * limit
        )().toList.map { row =>
        Map(
          "comment_id" -> row[String]("comments.comment_id"),
          "member_id" -> row[String]("comments.member_id"),
          "name" -> row[String]("members.name"),
          "email" -> row[String]("members.email"),
          "body" -> row[String]("comments.body"),
          "created" -> row[DateTime]("comments.created").toString("yyyy-MM-dd HH:mm")
        )
      }
    }
  }

  /**
   * Create new Comment
   * @param comment
   * @return Int
   */
  override def insert(comment: Map[String, String]): Try[Int] = Try {
    DB.withConnection { implicit connection =>
      SQL(
        """
              INSERT INTO   comments(comment_id,    member_id,   body,   modified,    created)
              VALUES                ({comment_id},  {member_id}, {body}, {created},   {created})
        """
      ).on(
          'comment_id -> comment("comment_id"),
          'member_id -> comment("member_id"),
          'body -> comment("body"),
          'created -> comment("created")
        ).executeUpdate()
    }
  }

  /**
   * get comment detail by Id
   * @param id
   * @return
   */
  override def selectById(id: String): Option[Map[String, String]] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          SELECT     comments.comment_id,
                     comments.member_id,
                     comments.created,
                     comments.body,
                     members.name,
                     members.email
          FROM       comments
          INNER JOIN members
          ON         members.member_id = comments.member_id
          WHERE      comments.comment_id = {comment_id}
        """)
        .on(
          'comment_id -> id
        ).singleOpt().map { row =>
        Map(
          "comment_id" -> row[String]("comments.comment_id"),
          "member_id" -> row[String]("comments.member_id"),
          "name" -> row[String]("members.name"),
          "email" -> row[String]("members.email"),
          "body" -> row[String]("comments.body"),
          "created" -> row[DateTime]("comments.created").toString("yyyy-MM-dd HH:mm")
        )
      }
    }
  }
}

