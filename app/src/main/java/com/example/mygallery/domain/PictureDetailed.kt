package com.example.mygallery.domain

sealed class PictureDetailed<Picture> {
    class Success<Picture>(val data: Picture) : PictureDetailed<Picture>()
    class Failure(val message: String) : PictureDetailed<Nothing>()
}