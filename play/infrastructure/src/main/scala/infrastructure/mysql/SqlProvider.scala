package infrastructure.mysql

import scala.util.Try

/**
 * Created by dell5460 on 7/29/2015.
 */
trait SqlProvider {
  def insert(info: Map[String, String]): Try[Int]

  def selectById(id: String): Option[Map[String, String]]
}
