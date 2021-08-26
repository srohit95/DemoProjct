package com.example.mainproject1.models

import com.google.gson.annotations.SerializedName

data class BaseInfo(@field:SerializedName("products") var products: List<Products>) {

    override fun toString(): String {
        return "BaseInfo{" +
                "products=" + products +
                '}'
    }

}