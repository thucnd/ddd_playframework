package domain.lifecycle.comment

import domain.model.comment.Comment
import infrastructure.mysql.CommentSqlProvider

/**
 * Created by dell5460 on 7/23/2015.
 */
trait CommentRepository {

  // Get Comment List In Db
  def findByOffset(offset: Int, limit: Int): List[Comment]

}

object CommentRepository extends CommentRepositoryOnDbImpl(new CommentSqlProvider)
