package com.ahoubouby.encupsilation.app3

import akka.actor.{Actor, ActorLogging}

object Person {
  // define messages
  case class SetFirstName(firstName: String)
  case class SetLastName(lastName: String)
  case class SetPhoneNumber(phoneNumber: String)
  case class AddRole(role: Role)
  case class Role(id: Int, roleName: Role)

  case class PersonalInformation(firstName: Option[String] = None,
                                 lastName: Option[String] = None,
                                 email: Option[String] = None,
                                 phone: Option[String] = None,
                                 roles: Set[Role] = Set.empty)
  case class Created(personalInformation: PersonalInformation)
}

class PerosnActor extends Actor with ActorLogging {

  import Person._

  override def receive: Receive = {
    case Created(personalInformation) =>
      context.become(createdPerson(personalInformation))
  }

  def createdPerson(personalInformation: PersonalInformation): Receive = {
    case SetFirstName(firstName) =>
      personalInformation.copy(firstName = Some(firstName))
  }
}
