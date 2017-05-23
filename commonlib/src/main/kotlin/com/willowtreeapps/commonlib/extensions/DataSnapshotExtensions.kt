package com.willowtreeapps.commonlib.extensions

import com.google.firebase.database.DataSnapshot
import com.willowtreeapps.commonlib.RobotArmBaseCallbacks
import com.willowtreeapps.commonlib.RobotArmComponentCallbacks

fun DataSnapshot.onBaseValueChange(baseCallbacks: RobotArmBaseCallbacks?) {
    baseCallbacks?.robotArmValueChange(this)
}

fun DataSnapshot.onComponentValueChange(armCompCallbacks: RobotArmComponentCallbacks?) {
    armCompCallbacks?.componentValueChange(this.ref?.parent?.key, this.value as String?)
}
