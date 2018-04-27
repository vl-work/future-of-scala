import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

// So to recall:

// A future is an asynchronous computation
Future(42) // <-- maybe we already know the answer

// It can be mapped to transform the result
Future.unit.map(_ => 42)

// It can be combined with other asynchronous
//  computations using .flatMap()
Future.unit.flatMap(_ => Future(42))

// You can also construct an already-evaluated one
Future.successful(42)

// Future[_] captures Exceptions (failures)
Future(throw new RuntimeException("Nope"))

// And allows it to be constructed from an existing exception
Future.failed(new RuntimeException("Nope"))

// We can work with sequences of futures
Future.sequence(Seq(Future(42), Future(42), Future(42)))

// And .sequence() is just a simplified version of .traverse() which allows
//    an arbitrary transformation to happen
Future.traverse(Seq(Future(1), Future(2), Future(3)))(_.map(_ * 2))

// In case something fails, you can recover
Future
  .failed(new NoSuchElementException("User with id 'bob' did not exist"))
  .recover {
    case _: NoSuchElementException => 'anonymousUser // perhaps return a default
  }

// Or try to do another recovery action such as using a different service instance
Future
  .failed(new RuntimeException("Service unavailable"))
  .recoverWith {
    case _ => Future(??? /* call another service instance */)
  }

// What about .map() on both a successful result *and* on the exception?
Future(42).transform(
  m => m + 1,
  e => new RuntimeException("Transformed", e)
)

// At the end - we need to actually wait for a result - with Await
import scala.concurrent.duration._

Await.result(Future(42), 1.second)

// Aside: Duration should be used whenever you have a time period (in logging, timeouts etc.)
// You can use syntax sugar to express any time unit
1.milli
2.nanos
3.hours
4.days

// You can also check for readiness
Await.ready(Future(42), 1.second)

// The Future[_] looks bright :)
