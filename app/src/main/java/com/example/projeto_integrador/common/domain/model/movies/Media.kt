package com.example.projeto_integrador.common.domain.model.movies


data class Media(val image: List<ImageSize>) {

    companion object {
        val emptyImage = MediaSizes.valueOf("")
    }

    fun getFirstAvailableImage(): MediaSizes {
        if (image.isEmpty()) return emptyImage

        return image.first().getAvailableImage()
    }
    data class ImageSize(
        val feedPoster: MediaSizes,
        val detailsPoster: MediaSizes
    ) {

        companion object {
            val emptyImage = MediaSizes.valueOf("")
        }

        fun getAvailableImage(): MediaSizes {
            return when {
                isValidImage(feedPoster) -> MediaSizes.W342
                isValidImage(detailsPoster) -> MediaSizes.W500
                else -> emptyImage
            }
        }

        private fun isValidImage(image: MediaSizes): Boolean {
            return image.toString().isNotEmpty()
        }
    }
}
