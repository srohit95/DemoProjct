package com.example.mainproject1.homescreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mainproject1.databinding.ActivityHomeScreenBinding
import com.example.mainproject1.models.BaseInfo
import com.example.mainproject1.models.Products
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.IndexOutOfBoundsException

class HomeScreenViewModel: ViewModel() {

    val name1 = MutableLiveData<String>()

    fun getJsonDataFromAsset(context: Context, fileName: String) : ArrayList<Products> {
        val gson = Gson()
        var productInformationsList : ArrayList<Products>

        name1.value = context.assets.open(fileName).bufferedReader().use { it.readText() }
        var productInformations  = gson.fromJson(context.assets.open(fileName).bufferedReader().use { it.readText() }, BaseInfo::class.java)
        productInformationsList  = productInformations.products as ArrayList<Products>

        return productInformationsList
    }

    fun loadDataInList( productArray: ArrayList<Products>, startIndex : Int, nosOfIndex : Int) : ArrayList<Products> {

        var dummyProducts = ArrayList<Products>()
       try {
           dummyProducts.addAll(productArray.subList(startIndex, startIndex + nosOfIndex))
        }
       catch (e : IndexOutOfBoundsException){
           dummyProducts.addAll(productArray.subList(startIndex, productArray.size))
       }
       catch (e: Exception) {
            e.printStackTrace()
        }

        return dummyProducts

    }

    fun sortDescendingByTitle(tmpProductArray : ArrayList<Products> ,bind : ActivityHomeScreenBinding) {
        tmpProductArray.sortByDescending { it.name }
        bind.list.adapter?.notifyDataSetChanged()
    }

    fun sortAscendingByTitle(tmpProductArray : ArrayList<Products> ,bind : ActivityHomeScreenBinding) {
        tmpProductArray.sortBy { it.name }
        bind.list.adapter?.notifyDataSetChanged()
    }

    fun sortDescendingByPrice(tmpProductArray : ArrayList<Products> ,bind : ActivityHomeScreenBinding) {
        tmpProductArray.sortByDescending { it.specialInt }
        bind.list.adapter?.notifyDataSetChanged()
    }

    fun sortAscendingByPrice(tmpProductArray : ArrayList<Products> ,bind : ActivityHomeScreenBinding) {
        tmpProductArray.sortBy { it.specialInt }
        bind.list.adapter?.notifyDataSetChanged()
    }

    fun sortDescendingByOffer(tmpProductArray : ArrayList<Products> ,bind : ActivityHomeScreenBinding) {
        tmpProductArray.sortByDescending {
            (100 - (it.specialInt!!.toDouble()/it.priceInt!!.toDouble())*100.0 ).toInt()
        }
        bind.list.adapter?.notifyDataSetChanged()
    }

    fun sortAscendingByOffer(tmpProductArray : ArrayList<Products> ,bind : ActivityHomeScreenBinding) {
        tmpProductArray.sortBy {
            (100 - (it.specialInt!!.toDouble()/it.priceInt!!.toDouble())*100.0 ).toInt()
        }
        bind.list.adapter?.notifyDataSetChanged()
    }

    fun filterPrice(tmpProductArray : ArrayList<Products> ,bind : ActivityHomeScreenBinding, fromPrice : Short, toPrice : Short) {

        try {
            var subProductArray = ArrayList<Products>()
            for(item in tmpProductArray){
                if(item.specialInt!!>=fromPrice&&item.specialInt!!<=toPrice){
                    subProductArray.add(item)
                }
            }
            tmpProductArray.clear()
            tmpProductArray.addAll(subProductArray!!)
            bind.list.adapter?.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun filterOffer(tmpProductArray : ArrayList<Products> ,bind : ActivityHomeScreenBinding, fromOffer : Byte, toOffer : Byte) {

        try {
            var subProductArray = ArrayList<Products>()
            for(item in tmpProductArray){

                val tempInt = (100 - (item.specialInt!!.toDouble()/item.priceInt!!.toDouble())*100.0 ).toInt()
                if(tempInt>=fromOffer&&tempInt<=toOffer){
                    subProductArray.add(item)
                }

            }
            tmpProductArray.clear()
            tmpProductArray.addAll(subProductArray!!)
            bind.list.adapter?.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}


