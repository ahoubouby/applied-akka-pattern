package com.ahoubouby.dataflow.pullpattern

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.actor.{ActorRef, Props}

object ProjectWorker {
  case class ScheduleProject(project: Project)
  case class ProjectScheduled(project: Project)

  def props(projectMaster: ActorRef) = Props(new ProjectWorker(projectMaster))
}

class ProjectWorker(projectMaster: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = ???

  private def scheduleProject(project: Project) = {
    ???
  }
}
