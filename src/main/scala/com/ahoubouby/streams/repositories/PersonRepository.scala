package com.ahoubouby.streams.repositories

trait PersonRepository {

  import scala.concurrent.Future

  def findByEmail(emailAddress: EmailAddress): Future[Person]
}
