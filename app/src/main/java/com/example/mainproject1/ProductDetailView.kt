package com.example.mainproject1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mainproject1.homescreen.HomeScreen
import com.example.mainproject1.models.Products

class ProductDetailView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail_view)

        val product : Products
        product = intent.getParcelableExtra<Products>("productInfo")!!
        replaceFragment(ProductDetailViewFragment(product))

    }

    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.hostFragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this, HomeScreen::class.java))
        finish()
    }
}