package com.moscow.tudee.presentation.designSystem.component.slider

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.moscow.tudee.R
import com.moscow.tudee.presentation.screen.home.HomeState.SliderState

sealed class SliderContent(
    @StringRes val title: Int,
    @DrawableRes val emojiState: Int,
    @StringRes val description: Int,
    @DrawableRes val imageResId: Int,
) {
    object StayWorking : SliderContent(
        title = R.string.stay_working,
        emojiState = R.drawable.ic_status_neutral,
        description = R.string.stay_working_description,
        imageResId = R.drawable.im_robot_neutral
    )

    object Tadaa : SliderContent(
        title = R.string.tadaa,
        emojiState = R.drawable.ic_status_happy,
        description = R.string.tadaa_description,
        imageResId = R.drawable.im_robot_happy
    )

    object ZeroProgress : SliderContent(
        title = R.string.zero_progress,
        emojiState = R.drawable.ic_status_angry,
        description = R.string.zero_progress_description,
        imageResId = R.drawable.im_robot_angry
    )

    object NothingOnYourList : SliderContent(
        title = R.string.Noting_to_do,
        emojiState = R.drawable.ic_status_sad,
        description = R.string.Noting_to_do_description,
        imageResId = R.drawable.im_robot_neutral
    )

    companion object {
        fun from(state: SliderState): SliderContent = when(state) {
            SliderState.STAY_WORKING -> StayWorking
            SliderState.TADAA -> Tadaa
            SliderState.ZERO_PROGRESS -> ZeroProgress
            SliderState.NOTHING_ON_YOUR_LIST -> NothingOnYourList
        }
    }
}
