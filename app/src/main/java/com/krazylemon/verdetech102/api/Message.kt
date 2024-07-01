package com.krazylemon.verdetech102.api

data class Message(
    val heat: String,
    val hum: String,
    val id: String,
    val posted_at: String,
    val smp_a: String,
    val smp_b: String,
    val smp_c: String,
    val smp_d: String,
    val smp_e: String,
    val temp: String
)