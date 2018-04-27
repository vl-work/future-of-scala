import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

type User
type Photo

def fetchUser(name: String): Future[User] = Future(???)

def fetchUserPhotos(user: User): Future[Seq[Photo]] = Future(???)

// Q: How do I get a user's photos given a username?
// Halp, below code does not work!

def naiveFetchPhotos(name: String): Seq[Photo] = {
  val userF: Future[User] = fetchUser(name)
  val photosF: Future[Seq[Photo]] = fetchUserPhotos(???)
  val photos: Seq[Photo] = ???
  photos
}

naiveFetchPhotos("bob")

// A: Use some of the methods on Future[_] :)

val life = Future()
val meaning = (_: Unit) => 42

life.map(meaning)
life.flatMap(meaning.andThen(Future(_)))

// Armed with this (these will become clearer with more examples)
// ...

// Ok, let's try:

def mappingUsers(name: String): Future[Seq[Photo]] = {
  val userF: Future[User] = fetchUser(name)
  // oops, doesn't compile
  val photosF: Future[Seq[Photo]] = ??? // userF.map(fetchUserPhotos)
  photosF
}

// What about flatMap?

def flatMappingUsers(name: String): Future[Seq[Photo]] = {
  val userF: Future[User] = fetchUser(name)
  val photosF: Future[Seq[Photo]] = userF.flatMap(fetchUserPhotos)
  photosF
}

// Or...

(name: String) => fetchUser(name).flatMap(fetchUserPhotos)

// Or ...

def fetchPhotos(name: String) = for {
  user <- fetchUser(name)
  photos <- fetchUserPhotos(user)
} yield photos

// Q: Wait, doesn't that still only return a Future[Seq[Photos]]
// A: Yup, good spot

// To wait for a the.future to finish - use Await.result()
// **BUT** only at the edges of asynchronous processing,
//          not in the middle

import scala.concurrent.duration._

Await.result(fetchPhotos("bob"), 60.seconds)
