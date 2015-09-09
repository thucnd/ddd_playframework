package infrastructure.mysql

import database._
import org.specs2.mutable.Specification


/**
 * Created by dell5460 on 7/8/2015.
 */
class MemberSqlProviderSpec extends Specification with FakeApp {
  override def tables = List(MemberTest.get)

  sequential

  "MemberSqlProvider" should {
    val memberSqlProvider = new MemberSqlProvider

    "def selectByEmail(email: String)" should {
      "Found email in DB" in {
        running(fakeApp) {
          memberSqlProvider.selectByEmail("thuc_nd@septeni-technology.jp") match {
            case Some(member) => {
              member("name") must beEqualTo("Nguyen Dinh Thuc")
              member("email") must beEqualTo("thuc_nd@septeni-technology.jp")
            }
          }
        }
      }
    }

    "def selectByEmail(email: String)" should {
      "Not Found email in DB" in {
        running(fakeApp) {
          memberSqlProvider.selectByEmail("thuc_nd123@septeni-technology.jp") must beNone
        }
      }
    }

    "def insert(member: Member): Try[Int]" should {
      "Insert success a new Member" in {
        running(fakeApp) {
          memberSqlProvider.insert(Map(
            "member_id" -> "1",
            "name" -> "Nguyen Dinh Thuc",
            "email" -> "thucnd@email.com",
            "password" -> "abcxyz"
          )) must beSuccessfulTry
        }
      }
    }

    "def insert(member: Member): Try[Int]" should {
      "Insert fails a new Member" in {
        running(fakeApp) {
          memberSqlProvider.insert(Map(
            "name" -> "Nguyen Dinh Thuc aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "email" -> "thucnd@email.com",
            "password" -> "abcxyz"
          )) must beFailedTry
        }
      }
    }
  }
}