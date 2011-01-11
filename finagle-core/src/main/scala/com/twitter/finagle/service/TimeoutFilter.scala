package com.twitter.finagle.service

import com.twitter.finagle.channel.TimedoutRequestException
import com.twitter.finagle.util.Conversions._
import com.twitter.finagle.util.Timer
import com.twitter.util.{Future, Duration, Throw}

/**
 * A filter to apply a global timeout to the request. This allows,
 * e.g., for a server to apply a global request timeout.
 */
class TimeoutFilter[Req <: AnyRef, Rep <: AnyRef](
  timer: Timer, timeout: Duration)
  extends Filter[Req, Rep, Req, Rep]
{
  def apply(request: Req, service: Service[Req, Rep]): Future[Rep] = {
    service(request).timeout(timer, timeout, Throw(new TimedoutRequestException))
  }
}
