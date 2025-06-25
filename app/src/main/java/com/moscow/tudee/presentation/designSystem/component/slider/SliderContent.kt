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
    open val totalTasks: Int = 0,
    open val doneTasks: Int = 0
) {

    data class StayWorking(
        override val totalTasks: Int,
        override val doneTasks: Int
    ) : SliderContent(
        title = R.string.stay_working,
        emojiState = R.drawable.ic_status_neutral,
        description = R.string.stay_working_description,
        imageResId = R.drawable.im_robot_neutral,
        totalTasks = totalTasks,
        doneTasks = doneTasks
    )

    data object Tadaa : SliderContent(
        title = R.string.tadaa,
        emojiState = R.drawable.ic_status_happy,
        description = R.string.tadaa_description,
        imageResId = R.drawable.im_robot_happy,
    )

    data object ZeroProgress : SliderContent(
        title = R.string.zero_progress,
        emojiState = R.drawable.ic_status_angry,
        description = R.string.zero_progress_description,
        imageResId = R.drawable.im_robot_angry,
    )

    data object NothingOnYourList: SliderContent(
        title = R.string.Noting_to_do,
        emojiState = R.drawable.ic_status_sad,
        description = R.string.Noting_to_do_description,
        imageResId = R.drawable.im_robot_neutral,
    )

    companion object {
        fun from(state: SliderState, totalTasks: Int = 0, doneTasks: Int=0): SliderContent = when (state) {
            SliderState.STAY_WORKING -> StayWorking(totalTasks, doneTasks)
            SliderState.TADAA -> Tadaa
            SliderState.ZERO_PROGRESS -> ZeroProgress
            SliderState.NOTHING_ON_YOUR_LIST -> NothingOnYourList
        }
    }
}