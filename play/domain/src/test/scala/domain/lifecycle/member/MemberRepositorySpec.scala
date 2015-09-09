package domain.lifecycle.member

import domain.model.member.Member
import infrastructure.mysql.MemberSqlProvider
import org.specs2.mock.Mockito
import play.api.test.{FakeApplication, PlaySpecification, WithApplication}

import scala.util.Try

/**
 * Created by dell5460 on 7/27/2015.
 */

class MemberRepositorySpec extends PlaySpecification with Mockito {
  private val mockMemberSqlProvider = mock[MemberSqlProvider]

  private def mockMemberRepository(memberSqlProvider: MemberSqlProvider) =
    new MemberRepositoryOnDBImpl(memberSqlProvider)

  val dummyData = Map(
    "member_id" -> "c5ef3623-0f6a-46ac-af2c-a520106cc2b3",
    "name" -> "Nguyen Dinh Thuc",
    "email" -> "thuc_nd@septeni-technology.jp",
    "password" -> "thuc_nd@septeni-technology.jp",
    "created" -> "2015-09-09 12:30:59"
  )

  "MemberRepository" should {
    "def findByEmail(email: String): Option[Member]" should {
      "Not Found email in DB" in new WithApplication {
        running(FakeApplication()) {
          mockMemberSqlProvider.selectByEmail(any[String]).returns(None)
          mockMemberRepository(mockMemberSqlProvider).findByEmail("thuc@gmail.com") must beNone
        }
      }
    }

    "def findByEmail(email: String): Option[Member]" should {
      "Found email in DB" in new WithApplication {
        running(FakeApplication()) {
          mockMemberSqlProvider.selectByEmail(any[String]).returns(Option(dummyData))
          mockMemberRepository(mockMemberSqlProvider).findByEmail("thuc_nd@septeni-technology.jp") map { member =>
            member.name must beEqualTo("Nguyen Dinh Thuc")
            member.email must beEqualTo("thuc_nd@septeni-technology.jp")
          }
        }
      }
    }

    "def store(entity: Member): Try[Member]" should {
      "Insert success a new Member into Db" in new WithApplication {
        running(FakeApplication()) {
          mockMemberSqlProvider.insert(any[Map[String, String]]).returns(Try(1))
          val result = mockMemberRepository(mockMemberSqlProvider).store(Member(
            "c5ef3623-0f6a-46ac-af2c-a520106cc2b3",
            "Nguyen Dinh Thuc",
            "thuc_nd@septeni-technology.jp",
            "basdadadadadasdad"
          ))
          result.name must beEqualTo("Nguyen Dinh Thuc")
          result.id.value must beEqualTo("c5ef3623-0f6a-46ac-af2c-a520106cc2b3")
        }
      }
    }

    "def store(entity: Member): Try[Member]" should {
      "Insert fails a new Member into Db" in new WithApplication {
        running(FakeApplication()) {
          mockMemberSqlProvider.insert(any[Map[String, String]]).returns(Try(0))
          try {
            mockMemberRepository(mockMemberSqlProvider).store(Member(
              "c5ef3623-0f6a-46ac-af2c-a520106cc2b3",
              "Nguyen Dinh Thuc",
              "thuc_nd@septeni-technology.jp",
              "basdadadadadasdad"
            ))
          } catch {
            case e: Exception => e.getMessage() must beEqualTo("Member created fails. Please try again later")
          }
        }
      }
    }
  }
}