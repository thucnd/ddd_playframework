package util

import play.api.mvc._

/**
 * Provide security features
 */
trait Secured {

  /**
   * Retrieve the connected user email.
   */
  private def getEmail(request: RequestHeader) = request.session.get("email")

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Unauthorized

  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(getEmail, onUnauthorized) { user =>
    Action(request => f(user)(request))
  }
}