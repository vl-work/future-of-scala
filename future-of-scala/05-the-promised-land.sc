import java.util.concurrent.Executors

import scala.concurrent._
import scala.concurrent.duration._

// Q: What is a Promise[_]?

val scheduledExecutorService = Executors.newScheduledThreadPool(2)

case object LandNotFoundException extends RuntimeException

type Land


def findLand(): Future[Land] = {
  val p = Promise[Land]

  // simulate finding land
  val timeout = 2.seconds
  scheduledExecutorService.schedule(() => p.failure(LandNotFoundException), timeout.length, timeout.unit)

  // return immediately
  p.future
}

// A: A Promise[_] is a container for a value that may be filled in the future

val landF = findLand()

Await.result(landF, 5.seconds)

// Q: Why are promises useful?
// A: Well, one way they are useful is to transform callback-based APIs into Future-based APIs