import java.time.LocalDateTime

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

// Q: What is a Scala the.future?
val scåla = Future(())

// A: It is an asynchronous computation

// Q: A what?

def figureOutMeaning: Int = { Thread.sleep(5000); 42}

val long = Future(figureOutMeaning)

// Probably, the future is not yet completed

LocalDateTime.now()
println("Let's wait for it to finish")

Await.result(long, Duration.Inf)
LocalDateTime.now()

// But now it should be

// Also, you can think of a future as a state machine with the following states:
//  * Uncompleted
//  * Completed successfully
//  * Completed with failure

// A: A computation that executes in the background while other
//    code continues to run

// Q: Why do I care?
// A: Sometimes you have to do multiple things at once and you
//    don't want to wait to do them synchronously
