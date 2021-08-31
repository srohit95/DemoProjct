package com.example.mainproject1.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mainproject1.databinding.ProductListTempletBinding
import com.example.mainproject1.interfaces.onItemClickListener
import com.example.mainproject1.models.Products

//, val itemClick: (Int, Int) -> Unit

class ProductListAdapter(private var productlist: ArrayList<Products>, private val onItemClickListener: onItemClickListener) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() ,
    Filterable {

    var productFilterList = ArrayList<Products>()

    init {
        productFilterList = productlist as ArrayList<Products>
    }

    class ViewHolder(val productbind: ProductListTempletBinding) : RecyclerView.ViewHolder(productbind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val info=ProductListTempletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(info)
    }

    override fun getItemCount(): Int {
        return productFilterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var products=productFilterList.get(position)
        holder.productbind.title.setText(products.name)
        holder.productbind.price.setText("MRP " + products.price)
        holder.productbind.price.setPaintFlags(holder.productbind.price.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.productbind.qty.setText(products.quantity)
        var qty:Int=0

        holder.productbind.offer.setText( (100 - (products.specialInt!!.toDouble()/products.priceInt!!.toDouble())*100.0 ).toInt().toString()+"% OFF" )

        holder.productbind.specialprice.setText("Offer " + products.special)
        holder.productbind.qty.setText(qty.toString())
        holder.productbind.add.setOnClickListener {
            qty++
            holder.productbind.qty.setText(qty.toString())
        }

        holder.productbind.remove.setOnClickListener{
            if (qty>0){
                qty--
                holder.productbind.qty.setText(qty.toString())
            }else{
                Toast.makeText(holder.productbind.root.context, "Add Quantity", Toast.LENGTH_SHORT).show()
            }
        }

        holder.productbind.mainLayout.setOnClickListener {
            onItemClickListener.onClickPosition(position,products)
        }

        // this addCart is for further updates
        holder.productbind.addCart.setOnClickListener(View.OnClickListener {

           /* if (qty > 0) {
                itemClick(position, qty)
            } else {
                Toast.makeText(holder.productbind.root.context, "Add Quantity", Toast.LENGTH_SHORT).show()
            }*/

        })


        var imgStr = "img"+position%39
        val resId: Int = holder.productbind.root.context.getResources().getIdentifier(imgStr, "drawable", holder.productbind.root.context.packageName)
        products.image = resId.toString()

        Glide.with(holder.productbind.root.context)
           .load(resId)
           .into(holder.productbind.img)


    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getFilter(): Filter {

        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                if (charSearch.isEmpty()) {
                    productFilterList = productlist as ArrayList<Products>
                } else {
                    val resultList = ArrayList<Products>()
                    for (row in productlist) {

                        if (row!!.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }

                    }
                    productFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = productFilterList
                return filterResults

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productFilterList = results?.values as ArrayList<Products>
                notifyDataSetChanged()
            }


        }

    }


}