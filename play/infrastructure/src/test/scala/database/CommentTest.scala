package database

/**
  * Created by dell5460 on 7/16/2015.
  */
object CommentTest {
  // Insert Fake Data
  val insertDb =
                """
                  |INSERT IGNORE INTO `comments` VALUES ('0271f114-4e1b-4b61-85be-bd569b416949', 'c5ef3623-0f6a-46ac-af2c-a520106cc2b3','Body Information','2015-07-07 14:08:20','2015-07-07 14:08:34');
                """
  // Remove Fake Data
  val removeDB = """truncate table comments"""

  def get = Map (
    "insertDb" -> insertDb,
    "removeDb" -> removeDB
  )
 }
