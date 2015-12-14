package mesos.proxy.protocol

import mesos.proxy.model.Status
import spray.json.DefaultJsonProtocol

trait MesosProxyJsonProtocol extends DefaultJsonProtocol {
  implicit val statusFormat = jsonFormat4(Status.apply)
}
