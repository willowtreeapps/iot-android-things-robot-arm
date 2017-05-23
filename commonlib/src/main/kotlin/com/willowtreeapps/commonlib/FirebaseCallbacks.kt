package com.willowtreeapps.commonlib

import com.google.firebase.database.DataSnapshot

interface RobotArmBaseCallbacks {
    fun robotArmValueChange(dataSnapshot: DataSnapshot?)
}

interface RobotArmComponentCallbacks {
    fun componentValueChange(which: String?, state: String?)
}
