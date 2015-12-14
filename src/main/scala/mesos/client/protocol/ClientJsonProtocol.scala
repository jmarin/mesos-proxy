package mesos.client.protocol

import mesos.client.model.ResponseError
import spray.json.DefaultJsonProtocol

trait ClientJsonProtocol extends DefaultJsonProtocol {
  implicit val responseFormat = jsonFormat1(ResponseError.apply)
}
