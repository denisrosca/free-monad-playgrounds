package com.example

import cats.data.{Coproduct, Reader}
import cats.free.Free
import cats.{Id, ~>}
import com.example.action._
import com.example.interpreter.{InstrumentActionsInterpreter, LiquidityActionsInterpreter}

object Strategy {

  type App[A] = Coproduct[InstrumentAction, LiquidityAction, A]
  val interpreter: App ~> Id = InstrumentActionsInterpreter or LiquidityActionsInterpreter

  def program(implicit I: InstrumentActions[App], L: LiquidityActions[App]): Free[App, List[InstrumentSnapshot]] = {
    import I._
    import L._
    for {
      all <- retrieveAllInstruments
      lit <- filterLit(all)
      dtvFiltered <- filterByDTV(lit, 0.5)
      r <- snapshot(dtvFiltered)
    } yield r
  }

  def prepareInstruments(implicit I: InstrumentActions[App]): Unit = {
    import I._
    for {
      all <- retrieveAllInstruments
      lit <- filterLit(all)
      dtvFiltered <- filterByDTV(lit, 0.5)
    } yield dtvFiltered
  }

  def instruments(implicit actions: InstrumentActions[App]) = {
  }

  def run = program.foldMap(interpreter)

  def main(args: Array[String]):Unit = {
    run
  }
}