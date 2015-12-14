package mesos.client.dns.protocol

import mesos.client.dns.model.Service
import spray.json.DefaultJsonProtocol

trait MesosDNSProtocol extends DefaultJsonProtocol {
  implicit val serviceFormat = jsonFormat4(Service.apply)
}
