package database

/**
 * Created by dell5460 on 7/16/2015.
 */
object MemberTest {
  // Insert Fake Data
  val insertDb =
                """
                  |INSERT IGNORE INTO `members`(member_id, name, email, password, modified, created) VALUES ('c5ef3623-0f6a-46ac-af2c-a520106cc2b3', 'Nguyen Dinh Thuc','thuc_nd@septeni-technology.jp', '25d55ad283aa400af464c76d713c07ad', now(), now());
                """

  // Remove Fake Data
  val removeDB = """truncate table members"""

  def get = Map(
    "insertDb" -> insertDb,
    "removeDb" -> removeDB
  )
}
