package services.login

import domain.lifecycle.member.MemberRepository
import domain.model.member.Member
import infrastructure.security.HashCreator

import scala.util.Try

/**
 * Created by dell5460 on 8/31/2015.
 */
class LoginService {
  def authenticate(email: String, password: String): Try[Member] = Try {
    MemberRepository.findByEmail(email) match {
      case Some(member) =>
        member.password == HashCreator.create(password) match {
          case true => member
          case _ => throw new Exception("Password invalid")
        }
      case _ => throw new Exception("User or password invalid")
    }
  }
}
