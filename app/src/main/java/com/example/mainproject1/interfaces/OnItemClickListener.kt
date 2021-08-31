package com.example.mainproject1.interfaces

import com.example.mainproject1.models.Products

interface onItemClickListener {
    fun onClickPosition(position: Int, products: Products)
}