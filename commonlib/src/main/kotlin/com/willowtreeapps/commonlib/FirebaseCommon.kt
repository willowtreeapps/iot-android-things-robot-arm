package com.willowtreeapps.commonlib

import android.util.Log
import com.google.firebase.database.*
import com.willowtreeapps.commonlib.extensions.onBaseValueChange
import com.willowtreeapps.commonlib.extensions.onComponentValueChange

class FirebaseCommon {

    private val TAG = FirebaseCommon::class.java.simpleName

    private lateinit var dbRefBaseState: DatabaseReference
    private lateinit var robotArmStateRotate: DatabaseReference
    private lateinit var robotArmStateArticulate: DatabaseReference
    private lateinit var robotArmStateScope: DatabaseReference
    private lateinit var robotArmStateElevate: DatabaseReference
    private lateinit var robotArmStateClaw: DatabaseReference

    fun initFirebaseRefs(armCompCallbacks: RobotArmComponentCallbacks) {
        this.initAllRefs()

        this.robotArmStateRotate.addValueEventListener(createArmValueEventListener(armCompCallbacks))
        this.robotArmStateElevate.addValueEventListener(createArmValueEventListener(armCompCallbacks))
        this.robotArmStateArticulate.addValueEventListener(createArmValueEventListener(armCompCallbacks))
        this.robotArmStateScope.addValueEventListener(createArmValueEventListener(armCompCallbacks))
        this.robotArmStateClaw.addValueEventListener(createArmValueEventListener(armCompCallbacks))
    }

    fun initFirebaseRefs(baseCallbacks: RobotArmBaseCallbacks) {
        initAllRefs()
        resetComponentStates()
        this.dbRefBaseState.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                dataSnapshot?.onBaseValueChange(baseCallbacks)
            }

            override fun onCancelled(databaseError: DatabaseError?) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError?.toException())
            }
        })
    }

    fun setRotateValue(state: String?) {
        robotArmStateRotate.setValue(state)
    }

    fun setElevateValue(state: String?) {
        robotArmStateElevate.setValue(state)
    }

    fun setArticulateValue(state: String?) {
        robotArmStateArticulate.setValue(state)
    }

    fun setScopeValue(state: String?) {
        robotArmStateScope.setValue(state)
    }

    fun setClawValue(state: String?) {
        robotArmStateClaw.setValue(state)
    }

    fun resetComponentStates() {
        this.robotArmStateArticulate.setValue(STATE_STOP)
        this.robotArmStateElevate.setValue(STATE_STOP)
        this.robotArmStateClaw.setValue(STATE_STOP)
        this.robotArmStateRotate.setValue(STATE_STOP)
        this.robotArmStateScope.setValue(STATE_STOP)
    }

    private fun initAllRefs() {
        val database = FirebaseDatabase.getInstance()

        this.dbRefBaseState = database.reference.child(KEY_ROBOT_BASE_STATE)

        this.robotArmStateArticulate = dbRefBaseState.child(KEY_ARTICULATE).child(KEY_ROBOT_ARM_STATE)
        this.robotArmStateElevate = dbRefBaseState.child(KEY_ELEVATE).child(KEY_ROBOT_ARM_STATE)
        this.robotArmStateClaw = dbRefBaseState.child(KEY_CLAW).child(KEY_ROBOT_ARM_STATE)
        this.robotArmStateRotate = dbRefBaseState.child(KEY_ROTATE).child(KEY_ROBOT_ARM_STATE)
        this.robotArmStateScope = dbRefBaseState.child(KEY_SCOPE).child(KEY_ROBOT_ARM_STATE)
    }

    private fun createArmValueEventListener(armComponentCallbacks: RobotArmComponentCallbacks) =
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    dataSnapshot?.onComponentValueChange(armComponentCallbacks)
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", databaseError?.toException())
                }
            }
}
