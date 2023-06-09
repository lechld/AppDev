package at.aau.edu.appdev.messenger.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import at.aau.edu.appdev.messenger.R
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val color: UserColor,
)

@Serializable
enum class UserColor(
    @StringRes val description: Int,
    @ColorRes val secondary: Int,
    @ColorRes val primary: Int
) {
    GREEN(R.string.Green, R.color.message_green, R.color.message_onGreen),
    VIOLET(R.string.Violet, R.color.message_violet, R.color.message_onViolet),
    ORANGE(R.string.Orange, R.color.message_orange, R.color.message_onOrange),
    GREY(R.string.Grey, R.color.message_grey, R.color.message_onGrey),
    BLUE(R.string.Blue, R.color.message_blue, R.color.message_onBlue),
    YELLOW(R.string.Yellow, R.color.message_yellow, R.color.message_onYellow);
}