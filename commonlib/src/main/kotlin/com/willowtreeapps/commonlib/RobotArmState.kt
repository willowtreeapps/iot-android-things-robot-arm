package com.willowtreeapps.commonlib

import com.google.firebase.database.DataSnapshot

data class RobotArmState(val armState: Map<String, String> = emptyMap())

/**
 * Helper function to build a RobotArmState object from a Firebase DataSnapshot
 *
 * @return A new RobotArmState object
 */
fun buildRobotState(dataSnapshot: DataSnapshot?): RobotArmState {
    val armMap = mutableMapOf<String, String>()

    dataSnapshot?.children?.forEach({
        armMap.put(it.key, it.child(KEY_ROBOT_ARM_STATE).value as String)
    })

    return RobotArmState(armState = armMap)
}
