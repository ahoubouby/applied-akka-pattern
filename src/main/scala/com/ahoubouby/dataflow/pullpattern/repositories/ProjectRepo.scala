package com.ahoubouby.dataflow.pullpattern.repositories

import com.ahoubouby.dataflow.pullpattern.Project

trait ProjectRepo {
  def nextUnscheduledProject(): Option[Project]
}

class ProjectRepoImpl() extends ProjectRepo {
  import scala.util.Random
  import java.util.UUID

  val random = new Random()
  override def nextUnscheduledProject(): Option[Project] = {
    val nextValue = random.nextInt(3)
//    if (nextValue > 2)
    Some(Project(nextValue.toString, UUID.randomUUID().toString))
//    else None
  }
}
