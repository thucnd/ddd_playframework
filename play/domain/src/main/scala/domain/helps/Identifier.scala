package domain.helps

/**
 * Created by dell5460 on 9/17/2015.
 */
trait Identifier[+A] {
  def value: A

  val isDefined: Boolean = true

  val isEmpty: Boolean = !isDefined

  override def equals(obj: Any) = obj match {
    case that: Identifier[_] =>
      value == that.value
    case _ => false
  }

  override def hashCode = 31 * value.##
}
