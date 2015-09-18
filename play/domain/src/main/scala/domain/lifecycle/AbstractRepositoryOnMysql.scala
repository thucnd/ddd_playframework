package domain.lifecycle

import domain.helps.Identifier
import infrastructure.mysql.SqlProvider

import scala.util.Success

/**
 * Created by dell5460 on 8/11/2015.
 */
abstract class AbstractRepositoryOnMysql[E, ID <: Identifier[String]] {
  protected val modelName: String

  protected val sqlProvider: SqlProvider

  protected def convertToMap(entity: E): Map[String, String]

  protected def convertToEntity(map: Map[String, String]): E

  def store(entity: E): E = {
    sqlProvider.insert(convertToMap(entity)) match {
      case Success(result) if result > 0 => entity
      case _ => throw new Exception(modelName + " created fails. Please try again later")
    }
  }

  def resolveById(id: ID): Option[E] = {
      sqlProvider.selectById(id.value).map(convertToEntity)
  }
}
