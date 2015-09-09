package controllers.comment

import domain.model.comment.{Comment, CommentId}
import domain.model.member.MemberId
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{JsBoolean, JsDefined}
import play.api.test._
import services.comment.CommentService

import scala.util.Try


/**
 * Created by dell5460 on 7/8/2015.
 */
@RunWith(classOf[JUnitRunner])
class CommentControllerSpec extends PlaySpecification with Mockito {

  val mockCommentService = mock[CommentService]

  def commentControllerWithMock(mockCommentService: CommentService) =
    new CommentController(mockCommentService)

  val dummyComment = Comment(
    CommentId("aaaaaaaaa"),
    MemberId("bbbbbbbbbb"),
    "Nguyen Dinh Thuc",
    "thuc_nd@septeni-technology.jp",
    "Cong Ty So 1 Chau A",
    ""
  )

  "Comment" should {

    "list() Get Comment list By offset, limit" in new WithApplication {
      running(FakeApplication()) {
        mockCommentService.commentListByPage(any[Int]) returns Try(List(dummyComment))
        val apiResult = call(commentControllerWithMock(mockCommentService).list(Option(1)),
          FakeRequest(GET, "/app/commentList?page=1")
            .withSession(
              "id" -> dummyComment.id.value,
              "email" -> dummyComment.email,
              "name" -> dummyComment.name)
        )

        val jsonResult = contentAsJson(apiResult)
        jsonResult \ ("success") must beEqualTo(JsDefined(JsBoolean(true)))

      }
    }

    "create() create Success a new Comment" in new WithApplication {
      running(FakeApplication()) {
        mockCommentService.createComment(any[String])(any) returns Try(dummyComment)
        val apiResult = call(commentControllerWithMock(mockCommentService).create(),
          FakeRequest(POST, "/app/createComment")
            .withFormUrlEncodedBody(
              "body" -> dummyComment.body
            ).withSession(
              "id" -> dummyComment.id.value,
              "email" -> dummyComment.email,
              "name" -> dummyComment.name)
        )


        val jsonResult = contentAsJson(apiResult)
        jsonResult \ ("success") must beEqualTo(JsDefined(JsBoolean(true)))
      }
    }
  }
}
