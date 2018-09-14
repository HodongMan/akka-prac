package com.hodong.chapter2

import akka.actor.{Props, ActorSystem, Actor, ActorRef}


case class DoubleValue(x: Int)
case object CreateChild2
case object Send
case class Response(x: Int)

class DoubleActor extends Actor {
    def receive = {
        case DoubleValue(number) => println(s"${self.path.name} Got the number $number")
        sender ! Response(number * 2)
    }
}

class ParentActor2 extends Actor {
    val random = new scala.util.Random
    var childs = scala.collection.mutable.ListBuffer[ActorRef]()

    def receive = {
        case CreateChild2 => childs ++= List(context.actorOf(Props[DoubleActor]))
        case Send => 
            println(s"Sending messages to child")
            childs.zipWithIndex map { case (child, value) => 
            child ! DoubleValue(random.nextInt(10))}
            case Response(x) => println(s"Parent : Response from child ${sender.path.name} is $x")
    }
}

object SendMessagesToChild extends App {
    val actorSystem = ActorSystem("Hello-Akka")
    val parent = actorSystem.actorOf(Props[ParentActor2], "parent")
    parent ! CreateChild2
    parent ! CreateChild2
    parent ! CreateChild2
    parent ! Send
}