package domain.lifecycle.comment

import domain.lifecycle.AbstractRepositoryOnMysql
import domain.model.comment.{Comment, CommentId}
import domain.model.member.MemberId
import infrastructure.mysql.{CommentSqlProvider, SqlProvider}

/**
 * Created by dell5460 on 7/23/2015.
 */
private[comment] class CommentRepositoryOnDbImpl(commentSqlProvider: CommentSqlProvider) extends AbstractRepositoryOnMysql[Comment] with CommentRepository {
  /**
   * Get Comment List by Offset, Limit
   * @param offset
   * @param limit
   * @return
   */
  def findByOffset(offset: Int, limit: Int): List[Comment] = {
    commentSqlProvider.selectByOffset(offset, limit).map { row =>
      Comment(
        CommentId(row("comment_id")),
        MemberId(row("member_id")),
        row("name"),
        row("email"),
        row("body"),
        row("created"))
    }
  }

  protected val modelName: String = "Comment"

  protected val sqlProvider: SqlProvider = commentSqlProvider

  protected def convertToMap(entity: Comment): Map[String, String] = Map(
    "comment_id" -> entity.id.value,
    "member_id" -> entity.memberId.value,
    "body" -> entity.body,
    "created" -> entity.created
  )

  type This = CommentRepositoryOnDbImpl
}



