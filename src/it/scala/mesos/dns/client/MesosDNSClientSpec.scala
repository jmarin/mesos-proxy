package mesos.dns.client

import mesos.client.dns.MesosDNSClient
import org.scalatest.{MustMatchers, FlatSpec}
import scala.concurrent.Await
import scala.concurrent.duration._

class MesosDNSClientSpec extends FlatSpec with MustMatchers {

  val timeout = 5.seconds

  "A Services request" must "return a list of running services" in {
    val maybeServices = Await.result(MesosDNSClient.queryService("grasshopper-parser"), timeout)
    maybeServices match {
      case Right(services) =>
        services.size mustBe 3
        services.foreach(s => s.service mustBe "_grasshopper-parser._tcp.marathon.mesos")
      case Left(failed) =>
        failed.desc mustBe "503 Service Unavailable"
        fail("SERVICE_UNAVAILABLE")
    }
  }

}
