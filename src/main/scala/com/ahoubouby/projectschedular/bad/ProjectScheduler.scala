package com.ahoubouby.projectschedular.bad

import akka.actor.{Actor, ActorLogging}

sealed trait Command
sealed trait Event

object ProjectScheduler {
  case class SchedulerProject(project: Project) extends Command
  case class ProjectScheduler(project: Project) extends Event

}

class ProjectScheduler extends Actor with ActorLogging {
  override def receive: Receive = ???
}
