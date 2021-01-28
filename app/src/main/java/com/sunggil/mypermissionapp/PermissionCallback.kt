package com.sunggil.mypermissionapp

interface PermissionCallback {
    fun grant()

    fun need()

    fun deny()
}