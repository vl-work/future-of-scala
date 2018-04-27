import java.util.concurrent.Executor

import scala.concurrent.ExecutionContext.Implicits.global

// Q: What just happened?

// A: We just imported an implicit global ExecutionContext

// Q: ???

// A: An [[scala.concurrent.ExecutionContext]] actually determined how
//      Future[_]s are evaluated asynchronously

import scala.concurrent._
import scala.concurrent.duration.Duration

// Let's summon an ExecutionContext
val ec: ExecutionContext = implicitly[ExecutionContext]

// What are the defaults?
//  * parallelism level is number of available processors by default
//  * uses a ForkJoinPool underneath
//    * need to signify blocking operations explicitly **caveats apply

// General tip: declare/import an ExecutionContext near main(), pass it
//    as an implicit everywhere else

// Ok, let's try making some (very very slow) random numbers

def slowRand(): Future[Int] = {
  Future {
    blocking {
      Thread.sleep(1000)
      scala.util.Random.nextInt()
    }
  }
}


val fRandomNumbers: Seq[Future[Int]] = (1 to 50).map(_ => slowRand())

// Also, are our futures actually doing anything?

// Let's print some thread names
the.future.dumpThreads()
  .filterKeys(_.getName.startsWith("scala-execution-context"))
  .keys
  .foreach(t => println(t.getName))


// Errr Seq[Future[Int]] - what do we do with that?

// Tip - it's **NOT** `.map(Await.result(_, timeout))`

// Enter Future.sequence
val randomNumbersF: Future[Seq[Int]] = Future.sequence(fRandomNumbers)
Await.result(randomNumbersF, Duration.Inf)

// But that's the topic of the next 'slide'

// We can define a non-asynchronous executor quite literally (Akka has this in the testkit)
val sameThreadExecutor: ExecutionContext = ExecutionContext.fromExecutor(new Executor() {
  override def execute(runnable: Runnable) = runnable.run()
})