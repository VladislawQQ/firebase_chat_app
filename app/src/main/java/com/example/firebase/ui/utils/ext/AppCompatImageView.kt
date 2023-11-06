package com.example.firebase.ui.utils.ext

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.firebase.R

val GLIDE_OPTIONS = RequestOptions()
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .priority(Priority.HIGH)

fun AppCompatImageView.setImage(
    imageUri: String? = null
) {
    Glide.with(context)
        .load(imageUri)
        .apply(GLIDE_OPTIONS)
        .into(this)
}

val GLIDE_OPTIONS_USER = RequestOptions()
    .circleCrop()
    .placeholder(R.drawable.ic_person)
    .error(R.drawable.ic_person)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .priority(Priority.HIGH)

fun AppCompatImageView.setProfileImage(
    imageUri: String? = null
) {
    Glide.with(context)
        .load(imageUri)
        .apply(GLIDE_OPTIONS_USER)
        .into(this)
}