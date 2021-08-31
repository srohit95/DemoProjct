package com.example.mainproject1.models

import android.os.Parcel
import android.os.Parcelable

class Products() :  Parcelable {
    var name: String? = null
    var id: String? = null
    var product_id: String? = null
    var image: String? = null
    var quantity: String? = null
    var price: String? = null
    var special: String? = null
    var description: String? = null
    var priceInt : Int? = null
    var specialInt : Int? = null

   constructor(parcel: Parcel) : this() {
      name = parcel.readString()
      id = parcel.readString()
      product_id = parcel.readString()
      image = parcel.readString()
      quantity = parcel.readString()
      price = parcel.readString()
      special = parcel.readString()
      description = parcel.readString()
      priceInt = parcel.readValue(Int::class.java.classLoader) as? Int
      specialInt = parcel.readValue(Int::class.java.classLoader) as? Int
   }

   override fun writeToParcel(parcel: Parcel, flags: Int) {
      parcel.writeString(name)
      parcel.writeString(id)
      parcel.writeString(product_id)
      parcel.writeString(image)
      parcel.writeString(quantity)
      parcel.writeString(price)
      parcel.writeString(special)
       parcel.writeString(description)
      parcel.writeValue(priceInt)
      parcel.writeValue(specialInt)
   }

   override fun describeContents(): Int {
      return 0
   }

   companion object CREATOR : Parcelable.Creator<Products> {
      override fun createFromParcel(parcel: Parcel): Products {
         return Products(parcel)
      }

      override fun newArray(size: Int): Array<Products?> {
         return arrayOfNulls(size)
      }
   }


}
