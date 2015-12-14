package mesos.proxy.http

import java.net.InetAddress
import java.time.Instant
import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.coding.{ Deflate, Gzip, NoCoding }
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import com.typesafe.config.Config
import mesos.proxy.model.Status
import mesos.proxy.protocol.MesosProxyJsonProtocol
import scala.concurrent.ExecutionContextExecutor
import spray.json._

trait HttpService extends MesosProxyJsonProtocol {

  implicit val system: ActorSystem
  implicit val executor: ExecutionContextExecutor
  implicit val materializer: ActorMaterializer

  val config: Config

  val log: LoggingAdapter

  val routes = {
    pathSingleSlash {
      get {
        encodeResponseWith(NoCoding, Gzip, Deflate) {
          complete {
            // Creates ISO-8601 date string in UTC down to millisecond precision
            val now = Instant.now.toString
            val host = InetAddress.getLocalHost.getHostName
            val status = Status("OK", "mesos-proxy", now, host)
            log.debug(status.toJson.toString())
            ToResponseMarshallable(status)
          }
        }
      }
    }
  }

}
