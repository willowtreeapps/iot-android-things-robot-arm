package com.willowtreeapps.androidthings_iot_robotarm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.willowtreeapps.commonlib.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val firebaseCommon = FirebaseCommon()

    private var buttonMap = emptyMap<String, Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonMap = mapOf(
                BUTTON_FORWARD_ROTATE to findViewById(R.id.buttonForwardRotate) as Button,
                BUTTON_FORWARD_ELEVATE to findViewById(R.id.buttonForwardElevate) as Button,
                BUTTON_FORWARD_ARTICULATE to findViewById(R.id.buttonForwardArticulate) as Button,
                BUTTON_FORWARD_SCOPE to findViewById(R.id.buttonForwardScope) as Button,
                BUTTON_FORWARD_CLAW to findViewById(R.id.buttonForwardClaw) as Button,
                BUTTON_STOP_ROTATE to findViewById(R.id.buttonStopRotate) as Button,
                BUTTON_STOP_ELEVATE to findViewById(R.id.buttonStopElevate) as Button,
                BUTTON_STOP_ARTICULATE to findViewById(R.id.buttonStopArticulate) as Button,
                BUTTON_STOP_SCOPE to findViewById(R.id.buttonStopScope) as Button,
                BUTTON_STOP_CLAW to findViewById(R.id.buttonStopClaw) as Button,
                BUTTON_REVERSE_ROTATE to findViewById(R.id.buttonReverseRotate) as Button,
                BUTTON_REVERSE_ELEVATE to findViewById(R.id.buttonReverseElevate) as Button,
                BUTTON_REVERSE_ARTICULATE to findViewById(R.id.buttonReverseArticulate) as Button,
                BUTTON_REVERSE_SCOPE to findViewById(R.id.buttonReverseScope) as Button,
                BUTTON_REVERSE_CLAW to findViewById(R.id.buttonReverseClaw) as Button
        ).onEach {
            it.value.setOnClickListener(this)
        }

        firebaseCommon.initFirebaseRefs(robotArmComponentCallbacks)
    }

    override fun onClick(view: View) {
        if (view is Button) {
            view.setButtonStyles(buttonMap)
            when (view.getButtonType(buttonMap)) {
                KEY_ROTATE -> firebaseCommon.setRotateValue(view.getButtonState(buttonMap))
                KEY_ELEVATE -> firebaseCommon.setElevateValue(view.getButtonState(buttonMap))
                KEY_ARTICULATE -> firebaseCommon.setArticulateValue(view.getButtonState(buttonMap))
                KEY_SCOPE -> firebaseCommon.setScopeValue(view.getButtonState(buttonMap))
                KEY_CLAW -> firebaseCommon.setClawValue(view.getButtonState(buttonMap))
            }
        }
    }

    private val robotArmComponentCallbacks = object : RobotArmComponentCallbacks {
        override fun componentValueChange(which: String?, state: String?) {
            getButton(buttonMap, which, state)?.setButtonStyles(buttonMap)
        }
    }
}
