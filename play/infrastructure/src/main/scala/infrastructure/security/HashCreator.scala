package infrastructure.security

/**
 * Created by dell5460 on 7/13/2015.
 */
object HashCreator {

  // Md5 function
  def create(s: String): String =
    java.security.MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8")).map(0xFF & _).map { "%02x".format(_) }.foldLeft(""){_ + _}
}
