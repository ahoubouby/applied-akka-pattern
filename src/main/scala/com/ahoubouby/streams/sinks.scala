package com.ahoubouby.streams

import akka.stream.SinkShape
import akka.stream.javadsl.Sink
import akka.stream.stage.GraphStage

object sinks {

  val printString = Sink.foreach[Any](println)
  val sinkIntgers = Sink.fold[Int, Int](0, { case (sum, value) => sum + value })

}

class Printer extends GraphStage[SinkShape[Int]] {

  import akka.stream.stage.GraphStageLogic
  import akka.stream.{Attributes, Inlet}

  private val in: Inlet[Int] = Inlet("NumberSink")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = {
    new GraphStageLogic(shape) {

      import akka.stream.stage.InHandler

      override def preStart(): Unit = {
        pull(in)
      }
      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          println(grab(in))
          pull(in)
        }
      })
    }
  }

  override def shape: SinkShape[Int] = SinkShape(in)
}
