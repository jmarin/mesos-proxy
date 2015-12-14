package mesos.dns.client.protocol

import mesos.dns.client.model.Status
import spray.json.DefaultJsonProtocol

trait MesosProxyJsonProtocol extends DefaultJsonProtocol {
  implicit val statusFormat = jsonFormat4(Status.apply)
}
