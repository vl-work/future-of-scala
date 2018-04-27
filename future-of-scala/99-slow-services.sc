import scala.concurrent._

type User
type UserName
type Graph[Node, NodeLabel, EdgeLabel]

trait UserService {
  def lookupUser(name: UserName): Future[User]
}

trait SocialGraphService {
  def lookupSocialGraph(user: User): Future[Graph[User, _, _]]
}

type RequestResult

object BackendService {
  val userService: UserService = ???
  val socialGraphService: SocialGraphService = ???

  def processRequest(name: UserName): RequestResult = {
    val user = userService.lookupUser(name)
    val socialGraph = socialGraphService.lookupSocialGraph(???)
    ???
  }
}

BackendService.processRequest(???)
