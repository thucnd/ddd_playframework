package domain.lifecycle.comment

import domain.model.comment.{CommentId, Comment}
import domain.model.member.MemberId
import infrastructure.mysql.CommentSqlProvider
import org.specs2.mock.Mockito
import play.api.test.{FakeApplication, PlaySpecification, WithApplication}

import scala.util.Try

/**
 * Created by dell5460 on 7/27/2015.
 */

class CommentRepositorySpec extends PlaySpecification with Mockito {
  private val mockCommentSqlProvider = mock[CommentSqlProvider]

  private def mockCommentRepository(commentSqlProvider: CommentSqlProvider) =
    new CommentRepositoryOnDbImpl(commentSqlProvider)

  val dummyData = Map(
    "comment_id" -> "0271f114-4e1b-4b61-85be-bd569b416949",
    "member_id" -> "c5ef3623-0f6a-46ac-af2c-a520106cc2b3",
    "name" -> "Nguyen Dinh Thuc",
    "email" -> "thuc_nd@septeni-technology.jp",
    "body" -> "Body Information",
    "created" -> "2015-09-09 12:30:59"
  )

  "CommentRepositorySpec" should {

    "findByOffset(offset: Int, limit: Int): Try[List[Comment]]" should {
      "Not Found comment in DB" in new WithApplication {
        running(FakeApplication()) {
          mockCommentSqlProvider.selectByOffset(any[Int], any[Int]) returns List()
          mockCommentRepository(mockCommentSqlProvider).findByOffset(2, 10) must equalTo(List())
        }
      }
    }

    "findByOffset(offset: Int, limit: Int): Try[List[Comment]]" should {
      "Found comment in DB" in new WithApplication {
        running(FakeApplication()) {
          mockCommentSqlProvider.selectByOffset(any[Int], any[Int]) returns List(dummyData)
          val comments = mockCommentRepository(mockCommentSqlProvider).findByOffset(1, 10)
          comments.head.name must beEqualTo("Nguyen Dinh Thuc")
          comments.head.email must beEqualTo("thuc_nd@septeni-technology.jp")
          comments.head.body must beEqualTo("Body Information")
        }
      }
    }

    "def store(entity: Comment): Try[Comment]" should {
      "Insert success a new Comment into Db" in new WithApplication {
        running(FakeApplication()) {
          mockCommentSqlProvider.insert(any[Map[String, String]]).returns(Try(1))
          val result = mockCommentRepository(mockCommentSqlProvider).store(Comment(
            CommentId("c5ef3623-0f6a-46ac-af2c-a520106cc2b4"),
            MemberId("c5ef3623-0f6a-46ac-af2c-a520106cc2b3"),
            "Nguyen Dinh Thuc",
            "thuc_nd@septeni-technology.jp",
            "basdadadadadasdad",
            "2015-09-09 12:00:00"
          ))
          result.name must beEqualTo("Nguyen Dinh Thuc")
          result.id.value must beEqualTo("c5ef3623-0f6a-46ac-af2c-a520106cc2b4")
        }
      }
    }

    "def store(entity: Comment): Try[Comment]" should {
      "Insert fails a new Comment into Db" in new WithApplication {
        running(FakeApplication()) {
          mockCommentSqlProvider.insert(any[Map[String, String]]).returns(Try(0))
          try {
            mockCommentRepository(mockCommentSqlProvider).store(Comment(
              CommentId("c5ef3623-0f6a-46ac-af2c-a520106cc2b4"),
              MemberId("c5ef3623-0f6a-46ac-af2c-a520106cc2b3"),
              "Nguyen Dinh Thuc",
              "thuc_nd@septeni-technology.jp",
              "basdadadadadasdad",
              "2015-09-09 12:00:00"
            ))
          } catch {
            case e: Exception => e.getMessage() must beEqualTo("Comment created fails. Please try again later")
          }
        }
      }
    }
  }
}