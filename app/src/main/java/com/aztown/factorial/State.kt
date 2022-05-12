package com.aztown.factorial

sealed class State

object Progress : State()
object Error : State()
class Result(val factorial: String) : State()

