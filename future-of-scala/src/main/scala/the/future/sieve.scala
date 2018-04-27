package the.future

import scala.annotation.tailrec
import scala.collection.immutable.SortedSet

object sieve {

  /**
    * Count the number of primes up to `n`
    * @see https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
    * @note code stolen from the internet ☠ ☠ ☠
    */
  def sieveOfEratosthenes(n: Int): Int = {
    @tailrec
    def recSieve(rem: SortedSet[Int], nPrimes: Int = 0): Int =
      if (rem.head > math.sqrt(n)) nPrimes + rem.size
      else recSieve(crossOut(rem.tail, rem.head), nPrimes + 1)

    def crossOut(nums: SortedSet[Int], p: Int) =
      nums -- (p * p to nums.last by p).iterator

    recSieve(SortedSet(2 until n: _*))
  }
}
