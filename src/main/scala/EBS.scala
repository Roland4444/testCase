import java.io.{File, PrintWriter}
import java.nio.file.{Files, Path, Paths}

import Essentials.{Photo, Voice}
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import com.typesafe.config._
class FirstActor extends Actor {
  var counter = 0
  def receive = {
    case x:String       => { counter += 1
      println(counter)
      println(x+"==>Just received     "+self.path)
      sender!"received"}
  }
}
object forTest{
  val voice = "Voice"
  val photo = "Photo"
  val nothing = "nothing"
}

class GetType extends Actor {

  def writeResult(): Unit ={
    val pr = new PrintWriter("outs");
    pr.write(returned);
    pr.close();
  }
  var returned: String=""
  def receive = {
    case f: Voice      => {
      println("==>Just received  Voice")
      returned = forTest.voice
      writeResult
    }
    case f: Photo      => {
      println("==>Just received  Photo")
      returned = forTest.photo
      writeResult
    }
    case _ => {
      println("nothing")
      returned = forTest.nothing
      writeResult
    }
  }
}
object Main extends App {
  import com.typesafe.config.ConfigFactory
  val setts = new String(Files.readAllBytes(new File("./config.conf").toPath))
  val config: Config = ConfigFactory.parseString(setts)
  val system = ActorSystem("hurricane", config)
  val FirstActor = system.actorOf(Props[FirstActor], name = "firstactor")
  val SecondActor = system.actorOf(Props[GetType], name = "secondactor")
  FirstActor.tell("mx", SecondActor)
}

class BLOCK{
  val system = ActorSystem("hurricane")
  val getsType = system.actorOf(Props[GetType], name = "getstype")

}