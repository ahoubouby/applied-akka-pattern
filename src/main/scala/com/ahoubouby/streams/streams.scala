package com.ahoubouby.streams

import akka.stream.scaladsl._
import com.ahoubouby.streams.repositories.{
  EmailAddress,
  Person,
  PersonRepository,
  Schedule,
  ScheduleRepository
}
object streams {

  import akka.{Done, NotUsed}
  import akka.actor.ActorSystem
  import akka.stream.ActorMaterializer

  import scala.concurrent.Future

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()

  val toEmailAddress = Flow[String].map(str => EmailAddress(str))
  val parrlism = 1
  val personRepository = new PersonRepository {

    import com.ahoubouby.streams.repositories.Person

    import scala.concurrent.Future

    override def findByEmail(emailAddress: EmailAddress): Future[Person] = ???
  }
  val findPerson = Flow[EmailAddress].mapAsync(parrlism)(
    email => personRepository.findByEmail(email)
  )

  val scheduleRepository = new ScheduleRepository {

    import com.ahoubouby.streams.repositories.Schedule

    import scala.concurrent.Future

    override def find(personId: String): Future[Schedule] = ???
  }

  val findSchedule = Flow[Person].mapAsync(parrlism)(
    person => scheduleRepository.find(person.id)
  )

  def toJson(s: Schedule): Schedule = s
  val toJson = Flow[Schedule].map(toJson)
  //Runnable Graph is look like that"s
  val data = Iterator.fill(2200)("ahoubouby@gmail.com")

  val r: Future[Done] = Source
    .fromIterator(() => data)
    .via(toEmailAddress)
    .via(findPerson)
    .via(findSchedule)
    .via(toJson)
    .runForeach(json => println(json))

}
