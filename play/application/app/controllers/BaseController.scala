package controllers

import play.api.mvc.Controller
import play.api.Play
import play.api.i18n.{Lang, Messages}

/**
 * Created by dell5460 on 9/15/2015.
 */
class BaseController extends Controller{

  // Set Multi language
  implicit val messages: Messages = play.api.i18n.Messages.Implicits.applicationMessages(
    Lang(Play.current.configuration.getString("lang.default").getOrElse("ja")), play.api.Play.current)
}
