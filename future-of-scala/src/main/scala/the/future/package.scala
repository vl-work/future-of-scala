package the

import scala.collection.JavaConverters._

package object future {

  def dumpThreads(): Map[Thread, Array[StackTraceElement]] = Thread.getAllStackTraces.asScala.toMap

}
