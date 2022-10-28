package com.diskusage.libraries.screens.navigation.data

data class ThirdScreenArgs(
    val text: String,
    val number: Int
)

object Arguments {
    const val SecondScreenArgs = "Foo"
    val ThirdScreenArgs = ThirdScreenArgs(
        text = "Bar",
        number = 562
    )
}
