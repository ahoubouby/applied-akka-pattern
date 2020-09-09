package com.ahoubouby.ask.app1

import akka.actor.Actor
import akka.actor.Props
import scala.concurrent.duration._
import akka.pattern.ask
import scala.concurrent.ExecutionContext
import akka.util.Timeout
import akka.pattern.{ask, pipe}
object events {
  implicit val timeout: Timeout = 5.seconds
  case class ProcessData(data: String)
  case class DataProcessed(data: String)

  def processDataStage1(data: String): String = s"${data} - stage1"
  def processDataStage2(data: String): String = s"${data} - stage2"
  def processDataStage3(data: String): String = s"${data} - stage3"
}

class Stage1() extends Actor {

  import events._

  import scala.concurrent.ExecutionContext
  val nextStage = context.actorOf(Props(new Stage2()))
  implicit val ec: ExecutionContext = context.dispatcher

  override def receive: Receive = {

    case ProcessData(data) =>
      val processedData = processDataStage1(data)
      val future = (nextStage ? ProcessData(processedData))
      future.pipeTo(sender())
  }
}

class Stage2() extends Actor {
  import events._

  val nextStage = context.actorOf(Props(new Stage3()))
  implicit val ec: ExecutionContext = context.dispatcher
  override def receive: Receive = {
    case ProcessData(data) =>
      val processedData = processDataStage2(data)
      (nextStage ? ProcessData(processedData)).pipeTo(sender())
  }
}

class Stage3() extends Actor {
  import events._

  override def receive: Receive = {
    case ProcessData(data) =>
      val processedData = processDataStage3(data)
      sender() ! DataProcessed(processedData)
  }
}
