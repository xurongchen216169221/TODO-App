package com.achjqz.todo.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achjqz.todo.databinding.TaskRecyclerItemBinding


class TaskAdapter(private val listener: OnTaskClickListener) :
    ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback()) {

    interface OnTaskClickListener {
        fun onTaskClick(task: Task)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        bindTaskItem(holder, task)
    }

    private fun bindTaskItem(holder: ViewHolder, item: Task) {
        holder.binding.apply {
            task = item
            root.setOnClickListener {
                listener.onTaskClick(item)
            }
            executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TaskRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class ViewHolder(
        val binding: TaskRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

}

private class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

}