package com.ahoubouby.dataflow.pullpattern

import akka.actor.ActorSystem
import com.ahoubouby.dataflow.pullpattern.ProjectWorker.ScheduleProject
import com.ahoubouby.dataflow.pullpattern.repositories.ProjectRepoImpl

object WorkPullingExample extends App {

  import java.util.concurrent.TimeUnit

  import scala.concurrent.duration.FiniteDuration

  implicit val system = ActorSystem()

  val master = system.actorOf(
    ProjectMaster
      .props(new ProjectRepoImpl(), FiniteDuration(1000, TimeUnit.MILLISECONDS))
  )
  val worker = system.actorOf(ProjectWorker.props(master))

  worker ! ScheduleProject(Project("1212", "hoge"))

  system.terminate()
}
