// Pass around `ExecutionContext` as an implicit

// Do not use `Await.result()` or `Await.ready()`

// Do not use Future[_] everywhere in your API - use it to wrap things at a service level

// Do not use the global ExecutionContext when you have special requirements for a particular thread-pool size
//  (e.g. connection pooling to a database)

// Use scalatest and the ScalaFutures trait to test your Future[_] code

// Do not use .onSuccess() or .onFailure(), from 2.12 they are deprecated

// Do use Future.successful() or Future.failed() when you have the value available already