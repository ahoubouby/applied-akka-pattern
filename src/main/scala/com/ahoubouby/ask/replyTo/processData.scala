package com.ahoubouby.ask.replyTo

import akka.actor.{Actor, Props}

object events {

  import akka.actor.ActorRef

  case class ProcessData(data: String, replyTo: ActorRef)
  case class DataProcessed(data: String)

  def processDataStage1(data: String): String = s"${data} - stage1"
  def processDataStage2(data: String): String = s"${data} - stage2"
  def processDataStage3(data: String): String = s"${data} - stage3"
}

class Stage1() extends Actor {
  import events._
  val nextStage = context.actorOf(Props(new Stage2()))

  override def receive: Receive = {

    case data: String =>
      val processedData = processDataStage1(data)
      nextStage.forward(ProcessData(processedData, sender()))

    case DataProcessed(processedData) => println(processedData)
  }
}

class Stage2() extends Actor {
  import events._
  val nextStage = context.actorOf(Props(new Stage3()))
  override def receive: Receive = {
    case ProcessData(data, replyTo) =>
      val processedData = processDataStage2(data)
      nextStage.forward(ProcessData(processedData, replyTo))
  }
}

class Stage3() extends Actor {
  import events._
  override def receive: Receive = {
    case ProcessData(data, replyTo) =>
      val processedData = processDataStage3(data)
      replyTo ! DataProcessed(processedData)
  }
}
