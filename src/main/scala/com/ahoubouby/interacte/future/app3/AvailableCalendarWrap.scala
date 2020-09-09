package com.ahoubouby.interacte.future.app3

import akka.actor.{Actor, ActorLogging}
import com.ahoubouby.repositories.AvailableCalendarRepo
object AvailableCalendarWrap {
  // message protocols
  case class Find(resource: String)
  case class ModifiedCalendar(someInfo: String)
}
class AvailableCalendarWrap(repo: AvailableCalendarRepo)
    extends Actor
    with ActorLogging {

  import AvailableCalendarWrap._
  import akka.pattern.pipe

  implicit val excutionContext = context.system.dispatcher

  override def receive: Receive = {
    case Find(resource) =>
      repo.find(resource).pipeTo(self)(sender = sender())
    case resource: repo.Resource =>
      sender() ! ModifiedCalendar(resource.toUpperCase())
  }
}
