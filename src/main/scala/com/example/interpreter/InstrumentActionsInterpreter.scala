package com.example.interpreter

import cats.{Id, ~>}
import com.example.Instrument
import com.example.action._

object InstrumentActionsInterpreter extends (InstrumentAction ~> Id) {
  override def apply[A](fa: InstrumentAction[A]): Id[A] = fa match {
    case RetrieveInstruments =>
      println("Retrieve instruments")
      List.empty[Instrument]
    case FilterLit(instruments) =>
      println("Filter lit")
      instruments
    case FilterDark(instruments) =>
      println("Filter dark")
      instruments
    case FilterByDTV(instruments, threshold) =>
      println("Filter by DTV")
      instruments
  }
}
