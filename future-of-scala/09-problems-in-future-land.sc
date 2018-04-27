// Q: Are there problems in Future[_] land?

// A: Maybe, let's see:

import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

// No referential transparency:


val counter = new AtomicInteger(0)
def addToCounter(n: Int): Future[Unit] = Future(counter.addAndGet(n))

Await.result(for {
  _ <- addToCounter(1)
  _ <- addToCounter(1)
} yield (), 1.second)

counter.get()
counter.set(0)

// is *not* the same as:

val addOne = addToCounter(1)

Await.result(for {
  _ <- addOne
  _ <- addOne
} yield (), 1.second)

println("Broken counter:")
counter.get()

type User
def findUser(name: String): Future[User] = Future(???)

def retry[T](f: Future[T], numRetries: Int) = ???

// Wait, how to implement retry?
// Hint: you can't, computation is already running

// Future[_] is eager - i.e. once created it runs immediately
// It represents a running computation, maybe the result, but the computation itself

// warning: naive implementation
def retryAgain[T](f: => Future[T], numRetries: Int): Future[T] =
  if (numRetries == 0) Future.failed(new RuntimeException("Out of retries"))
  else f.recoverWith {
    case _ => retryAgain(f, numRetries - 1)
  }

// Unable to control lazy vs. eager, asynchronous vs. synchronous

// Scheduling is not in standard library

// Future[_] is not cancelable (you have to implement cancellation yourself)

// Stream[_] is lazy but no option for it to be asynchronous
