package domain.lifecycle

import infrastructure.mysql.SqlProvider

import scala.util.Success

/**
 * Created by dell5460 on 8/11/2015.
 */
abstract class AbstractRepositoryOnMysql[T] {
  protected val modelName: String

  protected val sqlProvider: SqlProvider

  protected def convertToMap(entity: T): Map[String, String]

  def store(entity: T): T = {
    sqlProvider.insert(convertToMap(entity)) match {
      case Success(result) if result > 0 => entity
      case _ => throw new Exception(modelName + " created fails. Please try again later")
    }
  }
}
