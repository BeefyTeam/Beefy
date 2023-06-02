package com.example.beefy.data.response

import com.google.gson.annotations.SerializedName

data class HelloWorldResponse (
    @SerializedName("message"  ) var message  : String? = null,
)