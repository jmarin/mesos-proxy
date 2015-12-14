package mesos.proxy

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import mesos.proxy.http.HttpService

import scala.concurrent.ExecutionContextExecutor

object MesosClient extends App with HttpService {
  override implicit val system: ActorSystem = ActorSystem("mesos-proxy")
  override val log: LoggingAdapter = Logging(system, getClass)
  override implicit val executor: ExecutionContextExecutor = system.dispatcher
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
  override val config = ConfigFactory.load()

  val host = config.getString("mesos.proxy.host")
  val port = config.getInt("mesos.proxy.port")

  val http = Http(system).bindAndHandle(
    routes,
    host,
    port

  )

}
