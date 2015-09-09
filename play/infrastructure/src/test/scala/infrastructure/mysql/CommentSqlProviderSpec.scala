package infrastructure.mysql

import database._
import org.specs2.mutable.Specification


/**
 * Created by dell5460 on 7/8/2015.
 */
class CommentSqlProviderSpec extends Specification with FakeApp {
  override def tables = List(MemberTest.get, CommentTest.get)

  sequential

  "CommentSqlProvider" should {
    val commentSqlProvider = new CommentSqlProvider

    "def selectByOffset(offset: Int, limit: Int)" should {
      "get Comment List in DB" in {
        running(fakeApp) {
          val comments = commentSqlProvider.selectByOffset(1, 10)
          comments.head("body") must beEqualTo("Body Information")
          comments.head("name") must beEqualTo("Nguyen Dinh Thuc")
          comments.head("email") must beEqualTo("thuc_nd@septeni-technology.jp")
          comments.isEmpty must beFalse

        }
      }
    }

    "def selectByOffset(offset: Int, limit: Int)" should {
      "Not found Comment List in DB" in {
        running(fakeApp) {
          commentSqlProvider.selectByOffset(2, 10) must beEmpty
        }
      }
    }

    "def insert(comment: Map[String, String]): Try[Int]" should {
      "Insert success a new Comment" in {
        running(fakeApp) {
          commentSqlProvider.insert(Map(
            "member_id" -> "1",
            "comment_id" -> "1",
            "created" -> "2015-09-03 16:33:31",
            "body" -> "Body"
          )) must beSuccessfulTry
        }
      }
    }

    "def insert(comment: Map[String, String]): Try[Int]" should {
      "Insert fails a new Comment" in {
        running(fakeApp) {
          commentSqlProvider.insert(Map(
            "member_id" -> "aaaaaa",
            "body" -> "Body"
          )) must beFailedTry
        }
      }
    }
  }
}