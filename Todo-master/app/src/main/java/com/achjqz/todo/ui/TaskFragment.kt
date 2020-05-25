package com.achjqz.todo.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.achjqz.todo.R
import com.achjqz.todo.data.Task
import com.achjqz.todo.data.TaskAdapter
import com.achjqz.todo.databinding.TaskFragmentBinding
import com.achjqz.todo.utils.SHARED_REMIND_LIST
import com.achjqz.todo.viewmodels.TaskListViewModel

import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class TaskFragment : Fragment(), TaskAdapter.OnTaskClickListener {

    private lateinit var binding: TaskFragmentBinding
    private val taskViewModel by viewModel<TaskListViewModel>()
    private val taskSharedPreferences by inject<SharedPreferences>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskFragmentBinding.inflate(inflater, container, false)
        (requireActivity() as? MainActivity)?.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTask.setOnClickListener {
            fragmentManager!!.beginTransaction()
                .replace(R.id.content_container, AddTaskFragment()).addToBackStack("task").commit()
        }
        binding.toolbar.run {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete_all -> {
                        AlertDialog.Builder(requireContext())
                            .setMessage(requireContext().getString(R.string.delete_all_msg))
                            .setPositiveButton(requireContext().getString(R.string.delete)) { _, _ ->
                                taskViewModel.deleteAll()
                            }
                            .setNegativeButton(requireContext().getString(R.string.cancel_dialog)) { _, _ ->
                            }.show()
                    }
                }
                true
            }
        }
        //set adapter
        val taskAdapter = TaskAdapter(this)
        binding.rvTasksList.apply {
            adapter = taskAdapter
        }
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = (viewHolder as TaskAdapter.ViewHolder).binding.task!!
                taskViewModel.deleteTask(task)
                val remindList =
                    taskSharedPreferences.getStringSet(SHARED_REMIND_LIST, setOf())!!.toMutableSet()
                remindList.remove(task.title)
                taskSharedPreferences.edit {
                    putStringSet(SHARED_REMIND_LIST, remindList)
                }
                Snackbar.make(
                    binding.coordinator,
                    getString(R.string.delete_success),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.restore)) {
                        taskViewModel.addTask(task)
                        remindList.add(task.title)
                        taskSharedPreferences.edit {
                            putStringSet(SHARED_REMIND_LIST, remindList)
                        }
                    }.show()
            }

        })

        itemTouchHelper.attachToRecyclerView(binding.rvTasksList)
        taskViewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            if (tasks != null) {
                taskAdapter.submitList(tasks)
            }
        })
    }


    override fun onTaskClick(task: Task) {
        fragmentManager!!.beginTransaction()
            .replace(
                R.id.content_container,
                AddTaskFragment.newInstance(task.id, task.title, task.remark)
            )
            .addToBackStack("task").commit()
    }
}