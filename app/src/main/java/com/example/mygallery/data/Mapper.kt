package com.example.mygallery.data

import com.example.mygallery.data.cache.PictureEntity
import com.example.mygallery.data.cloud.PictureCloud
import com.example.mygallery.domain.Picture

class Mapper {
    companion object {
        fun pictureEntityToPicture(pictureEntity: PictureEntity): Picture {
            return Picture(pictureEntity.id,
                pictureEntity.author,
                pictureEntity.width,
                pictureEntity.height,
                pictureEntity.url,
                pictureEntity.downloadUrl)
        }

        fun pictureCloudToPicture(pictureCloud: PictureCloud): Picture {
            return Picture(pictureCloud.id,
                pictureCloud.author,
                pictureCloud.width,
                pictureCloud.height,
                pictureCloud.url,
                pictureCloud.downloadUrl)
        }

        fun pictureToPictureEntity(picture: Picture): PictureEntity {
            return PictureEntity(picture.id,
                picture.author,
                picture.width,
                picture.height,
                picture.url,
                picture.downloadUrl)
        }
    }
}