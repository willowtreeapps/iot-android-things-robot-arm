package com.willowtreeapps.androidthings_iot_robotarm

import android.app.Activity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.willowtreeapps.firebase.FirebaseCommon
import com.willowtreeapps.firebase.RobotArmBaseCallbacks
import com.willowtreeapps.firebase.buildRobotState

class MainActivity : Activity() {

    private val firebaseCommon = FirebaseCommon()
    private val robotGpio = RobotGpio()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        robotGpio.initGpio()

        firebaseCommon.initFirebaseRefs(object : RobotArmBaseCallbacks {
            override fun robotArmValueChange(dataSnapshot: DataSnapshot?) {
                robotGpio.toggleGpio(buildRobotState(dataSnapshot))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        robotGpio.closeAll()
    }
}
