package com.ahoubouby.ask.forward

import akka.actor.{Actor, Props}

object events {
  case class ProcessData(data: String)
  case class DataProcessed(data: String)

  def processDataStage1(data: String): String = s"${data} - stage1"
  def processDataStage2(data: String): String = s"${data} - stage2"
  def processDataStage3(data: String): String = s"${data} - stage3"
}

class Stage1() extends Actor {
  import events._
  val nextStage = context.actorOf(Props(new Stage2()))

  override def receive: Receive = {

    case ProcessData(data) =>
      val processedData = processDataStage1(data)
      nextStage.forward(ProcessData(processedData))
  }
}

class Stage2() extends Actor {
  import events._
  val nextStage = context.actorOf(Props(new Stage3()))
  override def receive: Receive = {
    case ProcessData(data) =>
      val processedData = processDataStage2(data)
      nextStage.forward(ProcessData(processedData))
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
