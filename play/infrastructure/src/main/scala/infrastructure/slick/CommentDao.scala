package infrastructure.slick

/**
 * Created by dell5460 on 8/18/2015.
 */

import infrastructure.slick.models.Comment
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

import scala.concurrent.Future

class CommentDao extends HasDatabaseConfig[JdbcProfile] {
  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  import driver.api._

  private val Comments = TableQuery[CommentsTable]

  def selectByOffset(offset: Int, limit: Int): Future[Seq[Comment]] = db.run(Comments.drop(offset).take(limit).result)

  private class CommentsTable(tag: Tag) extends Table[Comment](tag, "comments") {
    def commentId = column[Long]("comment_id", O.PrimaryKey)
    def memberId = column[Long]("member_id")
    def body = column[String]("body")
    def created = column[String]("created")
    def * = (commentId, memberId, body, created) <> (Comment.tupled, Comment.unapply _)
  }
}
