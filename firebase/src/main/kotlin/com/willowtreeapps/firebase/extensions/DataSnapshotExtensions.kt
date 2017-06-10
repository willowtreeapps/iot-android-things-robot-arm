package com.willowtreeapps.firebase.extensions

import com.google.firebase.database.DataSnapshot
import com.willowtreeapps.firebase.RobotArmBaseCallbacks
import com.willowtreeapps.firebase.RobotArmComponentCallbacks

fun DataSnapshot.onBaseValueChange(baseCallbacks: RobotArmBaseCallbacks?) {
    baseCallbacks?.robotArmValueChange(this)
}

fun DataSnapshot.onComponentValueChange(armCompCallbacks: RobotArmComponentCallbacks?) {
    armCompCallbacks?.componentValueChange(this.ref?.parent?.key, this.value as String?)
}
