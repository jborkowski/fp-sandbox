package com.jobo

import alleycats.Empty
import cats.syntax.foldable._
import cats.syntax.eq._
import cats.instances.list._
import cats.instances.int._
import cats.{Eq, Monoid}

object Brackets extends App {


  case class B(l: Int, r: Int)

  implicit val eqB = Eq[B] {
    cats.derived.auto.eq
    cats.derived.semi.eq
  }

  implicit val monoidB = new Monoid[B] {
    def empty: B =
      B(0, 0)

    def combine(x: B, y: B): B = (x, y) match {
      case (B(a, b), B(c, d)) if a <= d =>
        B(c, b + d - a)
      case (B(a, b), B(c, d)) =>
        B(c + a - d, b)
    }
  }

  def parse(char: Char): B = char match {
    case '(' => B(1,0)
    case ')' => B(0,1)
    case _   => B(0,0)
  }

  def balanced(list: List[Char]): Boolean =
    list.foldMap(parse) === Empty[B].empty


  if (balanced(args.mkString("").toList)) {
    println("Your string is balanced")
  } else {
    println("Your string is unbalanced")
  }

}
