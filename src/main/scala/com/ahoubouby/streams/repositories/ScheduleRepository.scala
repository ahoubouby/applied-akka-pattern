package com.ahoubouby.streams.repositories

trait ScheduleRepository {

  import scala.concurrent.Future

  def find(personId: String): Future[Schedule]
}
