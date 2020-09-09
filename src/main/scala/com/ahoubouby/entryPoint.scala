package com.ahoubouby

object entryPoint extends App {

  import akka.actor.ActorSystem
  import com.typesafe.config.ConfigFactory

  val conf = ConfigFactory.load("akka.conf")
  val system = ActorSystem("akka_applied_pattern", conf)

}
