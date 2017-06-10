package com.willowtreeapps.androidthings_iot_robotarm

import android.widget.Button
import com.willowtreeapps.firebase.*

val BUTTON_FORWARD_ROTATE = KEY_ROTATE + "_" + STATE_FORWARD
val BUTTON_FORWARD_ELEVATE = KEY_ELEVATE + "_" + STATE_FORWARD
val BUTTON_FORWARD_ARTICULATE = KEY_ARTICULATE + "_" + STATE_FORWARD
val BUTTON_FORWARD_SCOPE = KEY_SCOPE + "_" + STATE_FORWARD
val BUTTON_FORWARD_CLAW = KEY_CLAW + "_" + STATE_FORWARD

val BUTTON_STOP_ROTATE = KEY_ROTATE + "_" + STATE_STOP
val BUTTON_STOP_ELEVATE = KEY_ELEVATE + "_" + STATE_STOP
val BUTTON_STOP_ARTICULATE = KEY_ARTICULATE + "_" + STATE_STOP
val BUTTON_STOP_SCOPE = KEY_SCOPE + "_" + STATE_STOP
val BUTTON_STOP_CLAW = KEY_CLAW + "_" + STATE_STOP

val BUTTON_REVERSE_ROTATE = KEY_ROTATE + "_" + STATE_REVERSE
val BUTTON_REVERSE_ELEVATE = KEY_ELEVATE + "_" + STATE_REVERSE
val BUTTON_REVERSE_ARTICULATE = KEY_ARTICULATE + "_" + STATE_REVERSE
val BUTTON_REVERSE_SCOPE = KEY_SCOPE + "_" + STATE_REVERSE
val BUTTON_REVERSE_CLAW = KEY_CLAW + "_" + STATE_REVERSE

fun getButton(buttonMap: Map<String, Button>, armComponent: String?, state: String?) : Button? {
    return buttonMap["${armComponent.orEmpty()}_${state.orEmpty()}"]
}

fun Button.setButtonStyles(buttonMap: Map<String, Button>) {
    setActiveButtonStyle()
    getOpposingButtons(buttonMap).let {
        it.first?.setInactiveButtonStyle()
        it.second?.setInactiveButtonStyle()
    }
}

@Suppress("DEPRECATION")
fun Button.setActiveButtonStyle() {
    setBackgroundColor(resources.getColor(R.color.colorButtonBackgroundActive))
    setTextColor(resources.getColor(R.color.colorButtonActiveText))
}

@Suppress("DEPRECATION")
fun Button.setInactiveButtonStyle() {
    setBackgroundColor(resources.getColor(R.color.colorButtonBackgroundInactive))
    setTextColor(resources.getColor(R.color.colorButtonInactiveText))
}

fun Button.getButtonName(buttonMap: Map<String, Button>) : String? {
    return buttonMap.filterValues { it == this }.keys.first()
}

fun Button.getButtonType(buttonMap: Map<String, Button>) : String? {
    return getButtonName(buttonMap)?.substringBefore("_")
}

fun Button.getButtonState(buttonMap: Map<String, Button>) : String? {
    return getButtonName(buttonMap)?.substringAfter("_")
}

fun Button.getOpposingButtons(buttonMap: Map<String, Button>) : Pair<Button?, Button?> {
    when (getButtonName(buttonMap)) {
        BUTTON_FORWARD_ROTATE -> {
            return Pair(buttonMap[BUTTON_STOP_ROTATE], buttonMap[BUTTON_REVERSE_ROTATE])
        }
        BUTTON_FORWARD_ELEVATE -> {
            return Pair(buttonMap[BUTTON_STOP_ELEVATE], buttonMap[BUTTON_REVERSE_ELEVATE])
        }
        BUTTON_FORWARD_ARTICULATE -> {
            return Pair(buttonMap[BUTTON_STOP_ARTICULATE], buttonMap[BUTTON_REVERSE_ARTICULATE])
        }
        BUTTON_FORWARD_SCOPE -> {
            return Pair(buttonMap[BUTTON_STOP_SCOPE], buttonMap[BUTTON_REVERSE_SCOPE])
        }
        BUTTON_FORWARD_CLAW -> {
            return Pair(buttonMap[BUTTON_STOP_CLAW], buttonMap[BUTTON_REVERSE_CLAW])
        }

        BUTTON_STOP_ROTATE -> {
            return Pair(buttonMap[BUTTON_FORWARD_ROTATE], buttonMap[BUTTON_REVERSE_ROTATE])
        }
        BUTTON_STOP_ELEVATE -> {
            return Pair(buttonMap[BUTTON_FORWARD_ELEVATE], buttonMap[BUTTON_REVERSE_ELEVATE])
        }
        BUTTON_STOP_ARTICULATE -> {
            return Pair(buttonMap[BUTTON_FORWARD_ARTICULATE], buttonMap[BUTTON_REVERSE_ARTICULATE])
        }
        BUTTON_STOP_SCOPE -> {
            return Pair(buttonMap[BUTTON_FORWARD_SCOPE], buttonMap[BUTTON_REVERSE_SCOPE])
        }
        BUTTON_STOP_CLAW -> {
            return Pair(buttonMap[BUTTON_FORWARD_CLAW], buttonMap[BUTTON_REVERSE_CLAW])
        }

        BUTTON_REVERSE_ROTATE -> {
            return Pair(buttonMap[BUTTON_FORWARD_ROTATE], buttonMap[BUTTON_STOP_ROTATE])
        }
        BUTTON_REVERSE_ELEVATE -> {
            return Pair(buttonMap[BUTTON_FORWARD_ELEVATE], buttonMap[BUTTON_STOP_ELEVATE])
        }
        BUTTON_REVERSE_ARTICULATE -> {
            return Pair(buttonMap[BUTTON_FORWARD_ARTICULATE], buttonMap[BUTTON_STOP_ARTICULATE])
        }
        BUTTON_REVERSE_SCOPE -> {
            return Pair(buttonMap[BUTTON_FORWARD_SCOPE], buttonMap[BUTTON_STOP_SCOPE])
        }
        BUTTON_REVERSE_CLAW -> {
            return Pair(buttonMap[BUTTON_FORWARD_CLAW], buttonMap[BUTTON_STOP_CLAW])
        }

        else -> return Pair(first = null, second = null)
    }
}
