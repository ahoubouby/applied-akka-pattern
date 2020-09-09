package com.ahoubouby.interacte.future.app1

import akka.actor.{Actor, ActorLogging}
import com.ahoubouby.repositories.AvailableCalendarRepo
object AvailableCalendarWrap {
  // message protocols
  case class Find(resource: String)
}
class AvailableCalendarWrap(repo: AvailableCalendarRepo)
    extends Actor
    with ActorLogging {

  import AvailableCalendarWrap._
  implicit val excutionContext = context.system.dispatcher

  override def receive: Receive = {
    case Find(resource) =>
      repo.find(resource).foreach(result => sender() ! result)
    // sender may have changed!!!!!
  }
}
