package com.ahoubouby.consistency_scalability.app1

import akka.actor.ActorRef
import akka.persistence.{AtLeastOnceDelivery, PersistentActor}

object message {
  case class SendMessage(message: String)
  case class MessageSent(message: String)

  case class AcknowledgeDelivery(deliveryId: Long, message: String)
  case class DeliveryAcknowledged(deliveryId: Long)
}

object MySender {

  import akka.actor.{ActorRef, Props}

  def props(receiver: ActorRef) = Props(new MySender(receiver))
}

class MySender(receiver: ActorRef)
    extends PersistentActor
    with AtLeastOnceDelivery {

  import message._

  override def receiveRecover: Receive = {
    case MessageSent(message) =>
      deliver(receiver.path)(
        deliverId => AcknowledgeDelivery(deliverId, message)
      )
    case DeliveryAcknowledged(deliveryId) => confirmDelivery(deliveryId)
  }

  override def receiveCommand: Receive = {
    case SendMessage(message) =>
      persist(MessageSent(message)) { req =>
        deliver(receiver.path)(
          deliveryId => AcknowledgeDelivery(deliveryId, message)
        )
      }

    case ack: DeliveryAcknowledged =>
      persist(ack) { ack =>
        confirmDelivery(ack.deliveryId)
      }
  }

  override def persistenceId: String = "PersistenceID"
}
