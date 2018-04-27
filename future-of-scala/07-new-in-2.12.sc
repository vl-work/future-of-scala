import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

// Ok, we have a basic understanding of Future[_] now
// But - what's new?

// Future[Future[T]] - what to do?
Future(Future(42)).flatten

// .zipWith
Future('user).zip(Future('blogPosts))
Future('user).zipWith(Future('blogPosts)) {
  case (user, blogPosts) => () // combine them on the spot
}

// .transform works on Try[_] (and becomes more general)
import scala.util.{Success, Failure}

Future(42).transform {
  case Success(meaning) => Success(meaning + 1)
  case Failure(e) => Failure(new RuntimeException("Wrapped", e))
}

// recall .transform is a .map() of the value and the exception
// .transformWith() is like .flatMap() for the value and .recoverWith() for the exception

Future(42).transformWith {
  case Success(meaning) => Future(meaning + 1)
  case Failure(e) => Future(???) // perhaps go and try and resolve failure automatically
}

// .never mind
Future.never