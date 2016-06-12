package com.example.action

import cats.free.{Free, Inject}
import com.example.Instrument

sealed trait InstrumentAction[A]
case object RetrieveInstruments extends InstrumentAction[List[Instrument]]
case class FilterLit(instruments: List[Instrument]) extends InstrumentAction[List[Instrument]]
case class FilterByDTV(instruments: List[Instrument], threshold: Double) extends InstrumentAction[List[Instrument]]
case class FilterDark(instruments: List[Instrument]) extends InstrumentAction[List[Instrument]]

class InstrumentActions[F[_]](implicit I: Inject[InstrumentAction, F]) {

  def retrieveAllInstruments: Free[F, List[Instrument]] = Free.inject[InstrumentAction, F](RetrieveInstruments)

  def filterLit(instruments: List[Instrument]): Free[F, List[Instrument]] =
    Free.inject[InstrumentAction, F](FilterLit(instruments))

  def filterDark(instruments: List[Instrument]): Free[F, List[Instrument]] =
    Free.inject[InstrumentAction, F](FilterDark(instruments))

  def filterByDTV(instruments: List[Instrument], threshold: Double): Free[F, List[Instrument]] =
    Free.inject[InstrumentAction, F](FilterByDTV(instruments, threshold))

}

object InstrumentActions {
  implicit def instrumentActions[F[_]](implicit I: Inject[InstrumentAction, F]): InstrumentActions[F] = new InstrumentActions[F]
}