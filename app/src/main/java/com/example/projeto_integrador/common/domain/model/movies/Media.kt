package com.example.projeto_integrador.common.domain.model.movies


data class Media(val image: List<ImageSize>) {

    data class ImageSize(
        val feedPoster: MediaSizes,
        val detailsPoster: MediaSizes
    ) {


        fun getAvailableImage(): MediaSizes {
            return when {
                isValidImage(feedPoster) -> MediaSizes.w342
                isValidImage(detailsPoster) -> MediaSizes.w500
                else -> return MediaSizes.original
            }
        }

        private fun isValidImage(image: MediaSizes): Boolean {
            return image.toString().isNotEmpty()
        }
    }
}
