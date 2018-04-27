// Will use cats-effect IO[_] here
// Alternatives are fs2 (functional-streams-for-scala-2) and Monix

import cats.effect._

IO {
  42
}

// Ok, let's try again

IO {
  42
}.unsafeRunSync()

// Ah, we can run synchronously

val modified = for {
  meaning <- IO(42)
} yield meaning + 1

modified.unsafeRunAsync {
  case Left(ex) => ??? // handle exception
  case Right(value) => println(s"Modified meaning is $value")
}

