package services.signup

import domain.lifecycle.member.MemberRepository
import domain.model.member.Member
import infrastructure.security.HashCreator

import scala.util.Try

/**
 * Created by dell5460 on 9/1/2015.
 */
class SignUpService {

  def createMember(name: String, email: String, password: String): Try[Member] = Try {
    MemberRepository.findByEmail(email) match {
      case Some(member) => throw new Exception("User already existing")
      case None => MemberRepository.store(Member(name, email, HashCreator.create(password)))
    }
  }
}
