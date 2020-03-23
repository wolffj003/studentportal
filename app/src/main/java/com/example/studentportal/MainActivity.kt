package com.example.studentportal

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

const val ADD_REMINDER_REQUEST_CODE = 100


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var portals = arrayListOf<Portal>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createItemTouchHelper().attachToRecyclerView(rvPortals)

        viewManager = LinearLayoutManager(this)
        viewAdapter = PortalsAdapter(portals, { portal : Portal -> portalClicked(portal) })

        recyclerView = findViewById<RecyclerView>(R.id.rvPortals).apply {
            setHasFixedSize(true)   // Performance tweak
            layoutManager = viewManager
            adapter = viewAdapter
        }

        initViews() // TODO: Terugknoppie fixen
    }


    private fun initViews() {
        fabAddPortal.setOnClickListener{ onAddPortalClick() }
    }


    private fun portalClicked(portal: Portal) {
        if (URLUtil.isValidUrl(portal.urlText))
            launchCustomTab(portal.urlText)
        else Toast.makeText(this, getString(R.string.invalidURL), Toast.LENGTH_SHORT).show()
    }


    private fun launchCustomTab(url: String) {
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()

        customTabsIntent.launchUrl(this, Uri.parse(url))
    }


    private fun onAddPortalClick() {
        val intent = Intent(this, AddPortal::class.java)
        startActivityForResult(intent, ADD_REMINDER_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_REMINDER_REQUEST_CODE -> {
                    val portal = data!!.getParcelableExtra<Portal>(EXTRA_PORTAL)

                    portals.add(portal)
                    viewAdapter.notifyDataSetChanged()
                }
            }
        }
    }


    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                portals.removeAt(position)
                viewAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }
}
