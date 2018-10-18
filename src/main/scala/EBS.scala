import java.io.{BufferedOutputStream, File, FileOutputStream, PrintWriter}
import java.nio.file.{Files, Path, Paths}

import Essentials.{Photo, Voice, fullvoiceResult, voiceResult}
import RawImplements.callEBS_sound
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
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
class SimpleActor extends Actor {
  var counter = 0

  def receive = {
    case f: Any => {
      println("Just received "+f)

    }
  }
}

class GetType extends Actor {
  val cebs = new callEBS_sound
  def writeResult(): Unit ={
    val pr = new PrintWriter("outs");
    println("Writing now"+returned)
    pr.write(returned);
    pr.close();
  }
  var returned: String=""
  def receive = {
    case f: Voice => {
      println("IN VOICE SECTION")
      println(s"filename${f.filename}")
      println(s"lenhtg${f.wavContent.length}")
      returned = forTest.voice
      writeResult

      val bos = new BufferedOutputStream(new FileOutputStream(f.filename))
      bos write(f.wavContent)
      bos close

      val rc = cebs.call_ebs("./cv_configuration.json", s"./${f.filename}")
      new File(f.filename).delete()
      sender ! new fullvoiceResult(f.filename, new voiceResult(rc.checkResult, rc.lastErrorInSession, rc.ResultLoadingSoSymbols))


      println("==>Just received  Voice")
      println("result ->"+ rc.checkResult)

    }
    case f: Photo      => {
      returned = forTest.photo
      writeResult

      val bos = new BufferedOutputStream(new FileOutputStream(f.filename))
      bos write(f.photoContent)
      bos close

      println("==>Just received  Photo")

    }
    case _ => {

      println("nothing")
      returned = forTest.nothing
      writeResult
    }
  }
}
object Main extends App {
  def walkTree(file: File): Iterable[File] = {
    val children = new Iterable[File] {
      def iterator = if (file.isDirectory) file.listFiles.iterator else Iterator.empty
    }
    Seq(file) ++: children.flatMap(walkTree(_))
  }

  def create(filename: String): Voice={
    Voice(Files.readAllBytes(new File(filename).toPath), new File(filename).getName)
  }

  import com.typesafe.config.ConfigFactory
  val setts = new String(Files.readAllBytes(new File("./config.conf").toPath))
  val config: Config = ConfigFactory.parseString(setts)
  val system = ActorSystem("hurricane", config)
  val Simple = system.actorOf(Props[SimpleActor], name = "firstactor")
  val CheckActor = system.actorOf(Props[GetType], name = "secondactor")
  def checkPackets(filename: String, Checker: ActorRef, Sender: ActorRef)={
    var counter = 0
    for(f <- walkTree(new File(filename)) if f.getName.endsWith(".wav")) {
      counter += 1
      val created = create(f.getPath)
      Checker.tell(created, Sender)
    }
    Thread.sleep(4000)
    println(s"\n\n\nITERATIONS=>${counter}\n\n\n")
  }

  checkPackets("/home/roland/Downloads/download/.build_l64/tests_data/", CheckActor, Simple)


}

class BLOCK{
  val system = ActorSystem("hurricane")
  val getsType = system.actorOf(Props[GetType], name = "getstype")
  val simple = system.actorOf(Props[SimpleActor], name = "simpleActor")
}