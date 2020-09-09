package com.ahoubouby.repositories

trait AvailableCalendarRepo {

  import scala.concurrent.Future

  type Resource = String

  def find(id: Resource): Future[Resource]

}
