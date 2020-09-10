package com.ahoubouby.streams

import akka.stream.SourceShape
import akka.stream.stage.GraphStage

object sources extends App {

  val randomIntegers = new RandomIntegers()
}

class RandomIntegers extends GraphStage[SourceShape[Int]] {

  import akka.stream.stage.GraphStageLogic
  import akka.stream.{Attributes, Outlet}

  private val out: Outlet[Int] = Outlet("numbersSources")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = {
    new GraphStageLogic(shape) {
      import akka.stream.stage.OutHandler
      import scala.util.Random
      setHandler(out, new OutHandler {
        override def onPull(): Unit = push(out, Random.nextInt())
      })
    }
  }

  override def shape: SourceShape[Int] = SourceShape(out)

}
