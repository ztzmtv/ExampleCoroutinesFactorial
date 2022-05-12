package com.aztown.factorial

sealed class State

object Progress : State()
object Error : State()
class Factorial(val factorial: String) : State()

