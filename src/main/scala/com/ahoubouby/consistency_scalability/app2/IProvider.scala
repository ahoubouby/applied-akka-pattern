package com.ahoubouby.consistency_scalability.app2

import akka.persistence.PersistentActor

object IProvider {

  case object GenerateID
  case class IdGenereted(id: Int)

}

class IdProvider extends PersistentActor {
  import IProvider._

  private var currentId = 0;
  override def receiveRecover: Receive = {
    case IdGenereted(id) => currentId = id
  }

  override def receiveCommand: Receive = {
    case GenerateID =>
      persist(IdGenereted(currentId + 1)) { evt =>
        currentId = evt.id
        sender() ! evt
      }

  }

  override def persistenceId: String = "IdProvider"
}
