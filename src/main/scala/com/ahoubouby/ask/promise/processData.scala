package com.ahoubouby.ask.promise

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.{Await, Promise}
import com.typesafe.config.ConfigFactory

import scala.util.{Failure, Success}

object events {
  case class ProcessData(data: String, promise: Promise[String])

  def processDataStage1(data: String): String = s"${data} - stage1"
  def processDataStage2(data: String): String = s"${data} - stage2"
  def processDataStage3(data: String): String = s"${data} - stage3"
}

class Stage1() extends Actor {

  import events._

  val nextStage = context.actorOf(Props(new Stage2()))

  override def receive: Receive = {
    case ProcessData(data, promise) =>
      val processedData = processDataStage1(data)
      nextStage ! ProcessData(processedData, promise)
  }
}

class Stage2() extends Actor {
  import events._

  val nextStage = context.actorOf(Props(new Stage3()))
  override def receive: Receive = {
    case ProcessData(data, promise) =>
      val processedData = processDataStage2(data)
      nextStage ! ProcessData(processedData, promise)
  }
}

class Stage3() extends Actor {
  import events._
  import scala.util.Success

  override def receive: Receive = {
    case ProcessData(data, promise) =>
      val processedData = processDataStage3(data)
      promise.complete(Success(processedData))
  }
}

object entryPoint extends App {
  import akka.pattern.ask
  import scala.concurrent.duration._
  //************ Global definition  ************ //
  val conf = ConfigFactory.load("akka.conf")
  val system = ActorSystem("akka_applied_pattern", conf)

  //************ end of Global definition ************//

  //************ implicit section ************ //
  implicit val timeout = Timeout(5.seconds)
  implicit val excecutionContext = system.dispatcher
  //************ end of implicit section ************//
  val promise = Promise[String]()
  val stage = system.actorOf(Props(new Stage1()))
  val data: String = "abdelwahed stage processing "
  stage ! events.ProcessData(data, promise)
  val result = promise.future
  result.onComplete {
    case Success(v) => println(s"success: $v")
    case Failure(e) => println(s"error: $e")
  }

  Await.result(result, timeout.duration)
  system.terminate()
}
