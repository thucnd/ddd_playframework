package services.comment

import domain.lifecycle.comment.CommentRepository
import domain.model.comment.{CommentId, Comment}
import play.api.mvc.RequestHeader

import scala.util.Try

/**
 * Created by dell5460 on 9/1/2015.
 */
class CommentService {
  val MAX_POST = 10

  def commentListByPage(page: Int): Try[List[Comment]] = Try {
    CommentRepository.findByOffset(page, MAX_POST)
  }

  def commentDetail(id: String): Try[Comment] = Try {
    CommentRepository.resolveById(CommentId(id)) match {
      case Some(comment) => comment
      case  _ => throw new Exception("Comment not found in DB")
    }
  }

  def createComment(body: String)(implicit ctx: RequestHeader): Try[Comment] = Try {
    CommentRepository.store(
      Comment.create(
        ctx.session.get("id").getOrElse(""),
        ctx.session.get("name").getOrElse(""),
        ctx.session.get("email").getOrElse(""),
        body
      )
    )
  }
}
