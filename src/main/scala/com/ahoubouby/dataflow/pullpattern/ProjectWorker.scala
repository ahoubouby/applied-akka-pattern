package com.ahoubouby.dataflow.pullpattern

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.actor.{ActorRef, Props}

object ProjectWorker {
  case class ScheduleProject(project: Project)
  case class ProjectScheduled(project: Project)

  def props(projectMaster: ActorRef) = Props(new ProjectWorker(projectMaster))
}

class ProjectWorker(projectMaster: ActorRef) extends Actor with ActorLogging {
  import ProjectWorker._

  projectMaster ! ProjectMaster.RegisterWorker(self)
  override def receive: Receive = {
    case ScheduleProject(project) =>
      scheduleProject(project)
      log.info("project was called project = {}", project)
      projectMaster ! ProjectScheduled(project)
  }

  private def scheduleProject(project: Project) = {
    Project(project.id, s"${project.name}-was-in-worker")
  }
}
