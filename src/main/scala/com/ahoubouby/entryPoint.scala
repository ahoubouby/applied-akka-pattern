package com.ahoubouby

import akka.actor.{ActorSystem, Props}
import com.ahoubouby.ask.app1.{Stage1, events}
import com.typesafe.config.ConfigFactory

import akka.util.Timeout
import com.ahoubouby.ask.app1.events.DataProcessed

import scala.concurrent.Await
import scala.concurrent.duration._

object entryPoint extends App {
  import akka.pattern.ask
  //************ Global definition  ************ //
  val conf = ConfigFactory.load("akka.conf")
  val system = ActorSystem("akka_applied_pattern", conf)

  //************ end of Global definition ************//

  //************ implicit section ************ //
  implicit val timeout = Timeout(5.seconds)
  implicit val excecutionContext = system.dispatcher
  //************ end of implicit section ************//
  val stage = system.actorOf(Props(new Stage1()))
  val data: String = "abdelwahed stage processing "
  val future = (stage ? events.ProcessData(data))
  val result =
    Await.result(future, timeout.duration).asInstanceOf[DataProcessed]
  println(result)
  system.terminate()
}
