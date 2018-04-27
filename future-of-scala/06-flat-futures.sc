import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

// Recall that funny for {} yield syntax

// A for comprehension like this:
for {
  a <- List(1, 2, 3)
} yield a * 2

// Is the same as:
List(1, 2, 3).map(_ * 2)

// A for comprehension like this:
for {
  a <- List(1, 2, 3)
  b <- List(a, a *2)
} yield b

// Is the same as:
List(1, 2, 3).flatMap(a => List(a, a * 2)).map(identity)

// Future[_] can also flatMap as we saw in previous example

type User
type Photo

def fetchUser(name: String): Future[User] = Future(???)
def fetchUserPhotos(user: User): Future[Seq[Photo]] = Future(???)

for {
  user <- fetchUser("bob")
  photos <- fetchUserPhotos(user)
} yield photos

// for {} is quite powerful for writing programs that express their meaning

// But it's the same as:
fetchUser("bob").flatMap(fetchUserPhotos)