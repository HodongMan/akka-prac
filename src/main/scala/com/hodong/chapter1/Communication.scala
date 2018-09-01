package com.hodong.chapter1

import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import scala.util.Random._

object Messages {

    case class Done(randomNumber: Int)
    case object GiveMeRandomNumber
    case class Start(actorRef: ActorRef)
}

class RandomNumberGeneratorActor extends Actor {
    
    import Messages._
    override def receive: Receive = {
        case GiveMeRandomNumber => 
            println("received a message to generate a random number")
            val randomNumber = nextInt
            sender ! Done(randomNumber)
    }
}

class QueryActor extends Actor {
    import Messages._
    override def receive: Receive = {
        case Start(actorRef) => println(s"send me the random number")
            actorRef ! GiveMeRandomNumber
        case Done(randomNumber) => 
            println(s"received a random number $randomNumber")
    }
}

object Communication extends App {
    import Messages._
    val actorSystem = ActorSystem("HelloAkka")
    val RandomNumberGenerator = actorSystem.actorOf(Props[RandomNumberGeneratorActor], "randomNumberGeneratorActor")
    val queryActor = actorSystem.actorOf(Props[QueryActor], "queryActor")
    queryActor ! Start(RandomNumberGenerator)
}