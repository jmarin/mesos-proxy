package mesos.client.dns

import akka.http.scaladsl.unmarshalling.Unmarshal
import com.typesafe.config.{ ConfigFactory, Config }
import mesos.client.api.ServiceClient
import akka.http.scaladsl.model.StatusCodes._
import mesos.client.dns.model.Service
import mesos.client.dns.protocol.MesosDNSProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import mesos.client.model.ResponseError

import scala.concurrent.{ ExecutionContext, Future }

object MesosDNSClient extends ServiceClient with MesosDNSProtocol {
  override val config: Config = ConfigFactory.load()
  override lazy val host: String = config.getString("mesos.dns.host")
  override lazy val port: Int = config.getInt("mesos.dns.port")

  val version = config.getString("mesos.dns.version")

  def queryService(name: String): Future[Either[ResponseError, List[Service]]] = {
    implicit val ec: ExecutionContext = system.dispatcher
    val url = s"http://$host:$port/$version/services/_$name._tcp.marathon.mesos"
    sendGetRequest(url).flatMap { response =>
      response.status match {
        case OK => Unmarshal(response.entity).to[List[Service]].map(Right(_))
        case _ => sendResponseError(response)
      }
    }
  }

}
