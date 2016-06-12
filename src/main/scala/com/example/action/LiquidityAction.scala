package com.example.action

import cats.free.{Free, Inject}
import com.example.{Instrument, InstrumentSnapshot}

sealed trait LiquidityAction[A]

case class SnapshotInstruments(instrument: List[Instrument]) extends LiquidityAction[List[InstrumentSnapshot]]

class LiquidityActions[F[_]](implicit I:Inject[LiquidityAction,F]) {

  def snapshot(instruments: List[Instrument]): Free[F, List[InstrumentSnapshot]] =
    Free.inject[LiquidityAction,F](SnapshotInstruments(instruments))

}

object LiquidityActions {
  implicit def liquidityActions[F[_]](implicit inject: Inject[LiquidityAction, F]): LiquidityActions[F] = new LiquidityActions[F]
}