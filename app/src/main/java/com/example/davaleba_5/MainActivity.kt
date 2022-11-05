package com.example.davaleba_5

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var noteEditText: EditText
    private lateinit var addButton: Button
    private lateinit var recyclerview: RecyclerView
    private lateinit var data: ArrayList<ItemsViewModel>
    private lateinit var adapter: MainActivityAdapter
    private lateinit var info: String


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        registerListeners()
        data = ArrayList()
        adapter = MainActivityAdapter(data)
        recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
        recyclerview.layoutManager = LinearLayoutManager(this)



        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedCourse: ItemsViewModel = data.get(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition
                data.removeAt(viewHolder.adapterPosition)

                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(recyclerview, "Deleted " + deletedCourse.text, Snackbar.LENGTH_LONG)
                    .setAction("Undo", View.OnClickListener {

                        data.add(position, deletedCourse)
                        adapter.notifyItemInserted(position)
                    }).show()
            }

        }).attachToRecyclerView(recyclerview)


    }

    private fun init() {
        noteEditText = findViewById(R.id.noteEditText)
        addButton = findViewById(R.id.addButton)
        recyclerview = findViewById(R.id.recyclerView)

    }

    private fun registerListeners() {
        addButton.setOnClickListener {
            info = noteEditText.text.toString()
            if (info.isEmpty()) {
                Toast.makeText(this, "You didn't write anything", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            noteEditText.text.clear()
            data.add(ItemsViewModel(info))
            NoteDBHelper(this).apply {
                this.insert(info)

            }

        }
    }
}