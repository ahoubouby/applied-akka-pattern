package com.ahoubouby.dataflow.pullpattern

import akka.actor.{Actor, ActorLogging}
import akka.actor.{ActorRef, Props}
import com.ahoubouby.dataflow.pullpattern.repositories.ProjectRepo
import scala.concurrent.duration.FiniteDuration

object ProjectMaster {

  type ProjectID = String
  case class ProjectAdded(projectID: ProjectID)
  case class RegisterWorker(worker: ActorRef)
  private case class CheckForWork(worker: ActorRef)

  def props(projectRepo: ProjectRepo,
            pollingInterval: FiniteDuration): Props = {
    Props(new ProjectMaster(projectRepo, pollingInterval))
  }
}

class ProjectMaster(repo: ProjectRepo, pollingInterval: FiniteDuration)
    extends Actor
    with ActorLogging {

  import ProjectMaster._
  import context.dispatcher

  override def receive: Receive = {
    case RegisterWorker(worker) => scheduleNexProject(worker)
    case CheckForWork(worker)   => scheduleNexProject(worker)
    case projectAdded @ ProjectWorker.ProjectScheduled(_) =>
      log.debug("receive ProjectScheduled = {}", projectAdded)
      scheduleNexProject(sender())
  }

  private def scheduleNexProject(worker: ActorRef) = {
    log.info("call for nextProject {}", worker.path)
    repo.nextUnscheduledProject() match {
      case Some(project) => worker ! ProjectWorker.ScheduleProject(project)
      case None =>
        log.info(
          "next Project not found send self messafe to check for work for actorRef = {}",
          worker
        )
        context.system.scheduler
          .scheduleOnce(pollingInterval, self, CheckForWork(worker))
//        self ! CheckForWork(worker)
    }

  }
}
