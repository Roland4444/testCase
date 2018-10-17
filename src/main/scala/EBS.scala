import java.io.{BufferedOutputStream, File, FileOutputStream, PrintWriter}
import java.nio.file.{Files, Path, Paths}

import Essentials.{Photo, Voice}
import RawImplements.callEBS_sound
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

object EBS_replyes{
  val PASSED = "OK"
  val FAILED = "failed"


}

class GetType extends Actor {
  val cebs = new callEBS_sound
  def writeResult(): Unit ={
    val pr = new PrintWriter("outs");
    pr.write(returned);
    pr.close();
  }
  var returned: String=""
  def receive = {
    case f: Voice      => {
      val bos = new BufferedOutputStream(new FileOutputStream(f.filename))
      bos write(f.wavContent)
      bos close
      val rc = cebs.call_ebs("./cv_configuration.json", s"./${f.filename}")
      if (rc.checkResult==0)
        sender ! EBS_replyes.PASSED
      else
        sender ! EBS_replyes.FAILED
      val filename = new File(f.filename)
      filename.delete()

      println("==>Just received  Voice")
      returned = forTest.voice
      writeResult
    }
    case f: Photo      => {
      val bos = new BufferedOutputStream(new FileOutputStream(f.filename))
      bos write(f.wavContent)
      bos close


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