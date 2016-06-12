package com.example.interpreter

import cats.{Id, ~>}
import com.example.InstrumentSnapshot
import com.example.action.{LiquidityAction, SnapshotInstruments}

object LiquidityActionsInterpreter extends (LiquidityAction ~> Id) {
  override def apply[A](fa: LiquidityAction[A]): Id[A] = fa match {
    case SnapshotInstruments(instruments) =>
      println("Snapshot instrument data")
      List.empty[InstrumentSnapshot]
  }
}
