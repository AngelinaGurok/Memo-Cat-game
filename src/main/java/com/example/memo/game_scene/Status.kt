package com.example.memo.game_scene

enum class Status(val value: Int) {
    ITEM_DELETED(0),
    ITEM_OPEN(1),
    ITEM_CLOSED(2);

    fun getV() : Int{
        return this.value
    }
}