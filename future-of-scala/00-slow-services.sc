type User
type UserName
type Graph[Node, NodeLabel, EdgeLabel]

trait UserService {
  def lookupUser(name: UserName): User
}

trait SocialGraphService {
  def lookupSocialGraph(user: User): Graph[User, _, _]
}

type RequestResult

object BackendService {
  val userService: UserService = ???
  val socialGraphService: SocialGraphService = ???

  /**
    * Hypothetical method for inferring friends a user is most likely to have
    * based on their existing social graph
    */
  def inferFriends(name: UserName): RequestResult = {
    val user = userService.lookupUser(name) // <-- what if this is slow?
    val socialGraph = socialGraphService.lookupSocialGraph(user) // <-- and here?

    // do some more computation
    ???
  }
}

BackendService.inferFriends(???)
