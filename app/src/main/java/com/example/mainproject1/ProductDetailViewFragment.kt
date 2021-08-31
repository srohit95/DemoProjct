package com.example.mainproject1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mainproject1.databinding.DetailviewFragmentBinding
import com.example.mainproject1.models.Products

class ProductDetailViewFragment(val data : Products): Fragment() {


//    private var _binding: DetailviewFragmentBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v =  inflater.inflate(R.layout.detailview_fragment, container, false)

        val productTitle: TextView = v.findViewById(R.id.productTitle)
        val productMRP: TextView = v.findViewById(R.id.productMRP)
        val productoffer: TextView = v.findViewById(R.id.productoffer)
        val productDescription: TextView = v.findViewById(R.id.productDescription)
        val imageData: ImageView = v.findViewById(R.id.img)



        productTitle.setText(data.name)
        productMRP.setText(data.price)
        productoffer.setText(data.special)
        productDescription.setText(data.description)

        Glide.with(this)
            .load(data.image?.toInt())
            .into(imageData)


//        _binding = DetailviewFragmentBinding.inflate(R.layout.detailview_fragment, container, false)
//        val binding = FragmentBlankBinding.bind(view)
//        fragmentFirstBinding = binding





     /*   val btnNavigate: Button = v.findViewById(R.id.btnNavigate)
        val textView:TextView = v.findViewById(R.id.textView)*/



        /*val name = arguments?.getString("name")
        val age = arguments?.getInt("age")*/

       /* name?.let {
            textView.append("\nName: $name")
        }

        age?.let {
            textView.append("\nAge: $it")
        }*/


       /* // Replace fragment
        btnNavigate.setOnClickListener {
            // Pass data to fragment
            val args = Bundle()
            // Send string data as key value format
            args.putString("color","#FFFF00")
            val fragment = SecondFragment()
            fragment.arguments = args

            context.replaceFragment(fragment)
        }*/

        return v
    }




}