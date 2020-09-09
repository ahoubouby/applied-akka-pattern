package com.ahoubouby

import akka.actor.{ActorSystem, Props}
import com.ahoubouby.ask.replyTo.{Stage1, events}
import com.typesafe.config.ConfigFactory

import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

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
  val future = (stage ? data)
  future.onComplete {
    case Success(v) => println(s"success: $v")
    case Failure(e) => println(s"error: $e")
  }

  Await.result(future, timeout.duration)
  system.terminate()
}
