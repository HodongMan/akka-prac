package com.hodong.chapter1

import akka.actor.{Props, Actor, ActorSystem}

object BehaviorAndState extends App {
    val actorSystem = ActorSystem("HelloAkka")

    val actor = actorSystem.actorOf(Props[SummingActor], "ActorName")
    println(actor.path)
}

class SummingActor extends Actor {

  var sum = 0

  override def receive: Receive = {

    case x: Int => sum = sum + x
      println(s"my state as sum is $sum")
    // receives default message
    case _ => println("I don't know what are you talking about")

  }
}