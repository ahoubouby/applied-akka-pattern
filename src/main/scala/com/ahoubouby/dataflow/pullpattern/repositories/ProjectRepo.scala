package com.ahoubouby.dataflow.pullpattern.repositories

import com.ahoubouby.dataflow.pullpattern.Project

trait ProjectRepo {
  def nextUnscheduledProject(): Option[Project]
}
