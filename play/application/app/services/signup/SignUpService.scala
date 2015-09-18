package services.signup

import domain.lifecycle.member.MemberRepository
import domain.model.member.Member
import infrastructure.security.HashCreator
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

import scala.util.Try

/**
 * Created by dell5460 on 9/1/2015.
 */
class SignUpService {

  def createMember(name: String, email: String, password: String): Try[Member] = Try {
    MemberRepository.findByEmail(email) match {
      case Some(member) => throw new Exception(Messages("exception.user.inputInvalid"))
      case None => MemberRepository.store(Member.create(name, email, HashCreator.create(password)))
    }
  }
}
