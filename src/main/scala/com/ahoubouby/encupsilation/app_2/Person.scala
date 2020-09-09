package com.ahoubouby.encupsilation.app_2

import akka.actor.Actor

object Person {
  case class Role(id: Int, roleName: Role)
  case class PersonalInformation(firstName: Option[String] = None,
                                 lastName: Option[String] = None,
                                 email: Option[String] = None,
                                 phone: Option[String] = None,
                                 roles: Set[Role] = Set.empty)
}
class PerosnActor extends Actor {
  import Person._

  var personalInformation = PersonalInformation()
  override def receive: Receive = ???
}
