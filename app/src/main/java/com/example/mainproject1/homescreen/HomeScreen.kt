package com.example.mainproject1.homescreen


import android.app.AlertDialog
import android.app.Dialog
import android.app.SearchManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.SearchView


import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject1.R
import com.example.mainproject1.adapters.ProductListAdapter
import com.example.mainproject1.databinding.ActivityHomeScreenBinding
import com.example.mainproject1.databinding.FilterDialogBinding
import com.example.mainproject1.models.Products

class HomeScreen : AppCompatActivity() {

    lateinit var bind : ActivityHomeScreenBinding
    val nosOfIndex : Int = 20
    var loadingCount : Int = 0
    var adapter : ProductListAdapter? = null
    var tmpProductArray = ArrayList<Products>()
    var productInformations1 = ArrayList<Products>()
    var loadPermission : Boolean = true
    private val viewModel: HomeScreenViewModel by lazy {
        ViewModelProvider(this).get(HomeScreenViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(bind.root)

        try {

            productInformations1  = viewModel.getJsonDataFromAsset(applicationContext,"ProductInfo.json")
            tmpProductArray.addAll(viewModel.loadDataInList(productInformations1 ,0, nosOfIndex))
            adapter = ProductListAdapter(tmpProductArray)
            bind.list.adapter = adapter

            bind.list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {

                        if (loadPermission) {
                            if (tmpProductArray.size < productInformations1.size) {

                                loadingCount++
                                showToast("loading data")
                                tmpProductArray.addAll(viewModel.loadDataInList(productInformations1 ,nosOfIndex * loadingCount, nosOfIndex))
                                bind.list.adapter?.notifyDataSetChanged()

                            } else {
                                Toast.makeText(applicationContext,"No Data",Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter?.filter?.filter(p0)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapter?.filter?.filter(query)
                return true
            }
        })

        searchView.setOnCloseListener(
            SearchView.OnCloseListener {
                showToast("closed")
                false
            }
        )



        return super.onCreateOptionsMenu(menu)
    }

    private fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if(id == R.id.sortData){
            showSortDialog()
        }else if(id == R.id.filterData){
            showFilterDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showFilterDialog() {

        val options = arrayOf("Price : 499 - 999", "Price : 1000 - 1999" ,"Offer : 0% - 19%" ,"Offer : 20% - 39%", "Offer : 40% - 59%","Offer : 60% - 80%", "Clear Filters" )

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Filter")
            .setItems(options){
                    dialogInterface , i ->

                if (i == 0){
                    loadPermission = false
                    dialogInterface.dismiss()
                    refreshRecyclerView()
                    viewModel.filterPrice(tmpProductArray, bind, 499, 999)
                }
                else if( i == 1){
                    loadPermission = false
                    dialogInterface.dismiss()
                    refreshRecyclerView()
                    viewModel.filterPrice(tmpProductArray, bind, 1000, 1999)
                }
                else if( i == 2){
                    loadPermission = false
                    dialogInterface.dismiss()
                    refreshRecyclerView()
                    viewModel.filterOffer(tmpProductArray, bind, 0, 19)
                }
                else if( i == 3){
                    loadPermission = false
                    dialogInterface.dismiss()
                    refreshRecyclerView()
                    viewModel.filterOffer(tmpProductArray, bind, 20, 39)
                }
                else if( i == 4){
                    loadPermission = false
                    dialogInterface.dismiss()
                    refreshRecyclerView()
                    viewModel.filterOffer(tmpProductArray, bind, 40, 59)
                }
                else if( i == 5){
                    loadPermission = false
                    dialogInterface.dismiss()
                    refreshRecyclerView()
                    viewModel.filterOffer(tmpProductArray, bind, 60, 80)
                }
                else if( i == 6){
                    loadPermission = true  // loading data operation will work only if filters are cleared
                    refreshRecyclerView()
                }

            }.show()

    }

    private fun refreshRecyclerView() {
        tmpProductArray.clear()
        tmpProductArray.addAll(viewModel.loadDataInList(productInformations1 , 0, nosOfIndex * loadingCount + nosOfIndex))
        bind.list.adapter?.notifyDataSetChanged()
    }

    private fun showSortDialog() {

        val options = arrayOf("Title: A-Z" , "Title: Z-A" , "Price: Low to High" , "Price: High to Low" ,
        "Offer: Low to High" , "Offer: High to Low", "Clear All sort")

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Sort")
            .setItems(options){
                dialogInterface , i ->

                if (i == 0){
                    loadPermission = false
                    dialogInterface.dismiss()
                    viewModel.sortAscendingByTitle(tmpProductArray, bind)
                }
                else if( i == 1){
                    loadPermission = false
                    dialogInterface.dismiss()
                    viewModel.sortDescendingByTitle(tmpProductArray, bind)
                }
                else if (i == 2){
                    loadPermission = false
                    dialogInterface.dismiss()
                    viewModel.sortAscendingByPrice(tmpProductArray, bind)
                }
                else if( i == 3){
                    loadPermission = false
                    dialogInterface.dismiss()
                    viewModel.sortDescendingByPrice(tmpProductArray, bind)
                }
                else if (i == 4){
                    loadPermission = false
                    dialogInterface.dismiss()
                    viewModel.sortAscendingByOffer(tmpProductArray, bind)
                }
                else if( i == 5){
                    loadPermission = false
                    dialogInterface.dismiss()
                    viewModel.sortDescendingByOffer(tmpProductArray, bind)
                }
                else if(i == 6){
                    loadPermission = true
                    refreshRecyclerView()
                }

            }.show()

    }

    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Wish to exit application?")
        builder.setPositiveButton("Yes"){dialogInterface, which ->

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.finishAffinity()
            } else{
                this.finish()
                System.exit(0)
            }
        }
        builder.setNegativeButton("No"){dialogInterface, which ->

        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}




