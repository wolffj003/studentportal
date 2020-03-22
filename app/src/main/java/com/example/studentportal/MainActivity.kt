package com.example.studentportal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        viewManager = LinearLayoutManager(this)
        viewAdapter = PortalsAdapter(portals)

        recyclerView = findViewById<RecyclerView>(R.id.rvPortals).apply {
            setHasFixedSize(true)   // Performance tweak
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }


    private fun initViews() {
        fabAddPortal.setOnClickListener{ onAddPortalClick() }
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

}
