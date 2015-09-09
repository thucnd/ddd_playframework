package database

import anorm._
import org.specs2.specification.{AfterEach, BeforeEach}
import play.api.Play.current
import play.api.db.DB
import play.api.test.{FakeApplication, PlaySpecification}

/**
 * Created by dell5460 on 7/15/2015.
 */
trait FakeApp extends PlaySpecification with BeforeEach with AfterEach {

  def tables: List[Map[String, String]]

  def fakeApp = FakeApplication(
    additionalConfiguration = Map(
      "db.default.driver" -> "com.mysql.jdbc.Driver",
      "db.default.url" -> "jdbc:mysql://localhost/test?characterEncoding=UTF-8",
      "db.default.user" -> "root",
      "db.default.password" -> "123456"
    )
  )

  def before = {
    running(fakeApp) {
      DB.withConnection { implicit connection =>
        tables.map(table =>
          SQL(table("insertDb").stripMargin).execute()
        )
      }
    }
  }

  def after = {
    running(fakeApp) {
      DB.withConnection { implicit c =>
        tables.map(table =>
          SQL(table("removeDb").stripMargin).execute()
        )
      }
    }
  }
}
