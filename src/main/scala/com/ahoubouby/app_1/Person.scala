package com.ahoubouby.app_1

import akka.actor.{Actor, ActorLogging}

object Person {
  // define messages
  case class SetFirstName(firstname: String)
  case class SetLastName(lastname: String)
  case class SetPhoneNumber(phoneNumber: String)
  case class AddRole(role: Role)
  case class Role(id: Int, roleName: Role)

}

// problem with that's approche complixety aggement every time we need
// to add more detail for user
// what if we need to add some sort of validation logic to thats

class PersonActor extends Actor with ActorLogging with PhoneValidator {
  import Person._

  private var firstName: Option[String] = None
  private var lastName: Option[String] = None
  private var phoneNumber: Option[String] = None
  private var roles: Set[Role] = Set.empty

  override def receive: Receive = {
    case SetFirstName(fName) => firstName = Some(fName)
    case SetLastName(lName)  => lastName = Some(lName)
    case AddRole(role)       => roles = roles + role
    case SetPhoneNumber(pNumber) if validePhoneNumber(pNumber) =>
      phoneNumber = Some(pNumber)

  }
}
