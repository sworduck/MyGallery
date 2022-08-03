package com.example.mygallery.domain

sealed class Status {
    class Success : Status()
    class Fail : Status()
}
