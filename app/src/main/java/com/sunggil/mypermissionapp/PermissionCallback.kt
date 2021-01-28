package com.sunggil.mypermissionapp

interface PermissionCallback {
    fun grant()

    fun need(permission : String)

    fun deny()
}