package com.jobo

import cats.implicits._, cats._, cats.syntax._

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
      case (B(a, b), B(c, d)) if b <= c =>
        B(a + c - b, d)
      case (B(a, b), B(c, d)) =>
        B(a, d + b -c)
    }
  }

  def parse(char: Char): B = char match {
    case '(' => B(0,1)
    case ')' => B(1,0)
    case _   => B(0,0)
  }

  def balanced(list: List[Char]) =
    list.foldMap(parse) === B(0,0)



  if (balanced(args.mkString("").toList)) {
    println("Your string is balanced")
  } else {
    println("Your string is unbalanced")
  }

}
