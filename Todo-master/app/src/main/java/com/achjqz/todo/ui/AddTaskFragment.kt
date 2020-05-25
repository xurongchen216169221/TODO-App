package com.achjqz.todo.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.achjqz.todo.R
import com.achjqz.todo.data.Task
import com.achjqz.todo.databinding.AddTaskFragmentBinding
import com.achjqz.todo.reminder.TaskNotificationReceiver
import com.achjqz.todo.utils.*
import com.achjqz.todo.viewmodels.TaskListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {
    private lateinit var binding: AddTaskFragmentBinding
    private val taskViewModel by viewModel<TaskListViewModel>()
    private val taskSharedPreferences by inject<SharedPreferences>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTaskFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong(TASK_ID)
        arguments?.let {
            val title = it.getString(TASK_TITLE)
            val url = it.getString(TASK_URL)
            binding.addTask.text = getString(R.string.modify)
            binding.etTodoTitle.setText(title)
            binding.etTodoRemark.setText(url)
        }
        var priority = 3
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chip_unimportant -> priority = 1
                R.id.chip_important -> priority = 3
                R.id.chip_alert -> priority = 5
            }
        }

        binding.addTask.setOnClickListener {

            val calendar = Calendar.getInstance()
            calendar.time = binding.dateSelector.date

            val title = binding.etTodoTitle.text.toString()
            val remark = binding.etTodoRemark.text.toString()
            if (title.isBlank()) {
                requireContext().showToast(getString(R.string.title_error))
                return@setOnClickListener
            }
            val formatter = SimpleDateFormat("MMM dd hh:mm a", Locale.US)

            val task = Task(
                title = title,
                imgUrl = randomImg,
                remark = remark,
                subtitle = formatter.format(binding.dateSelector.date),
                priority = priority,
                timestamp = getCurrentTime()
            )
            if (binding.reminder.isChecked) {
                loge("set reminder")
                val alarmIntent = Intent(requireActivity(), TaskNotificationReceiver::class.java)
                alarmIntent.putExtra(INTENT_TITLE, title)
                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    title.hashCode(),
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                val manager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
                manager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

                val remindList =
                    taskSharedPreferences.getStringSet(SHARED_REMIND_LIST, setOf())!!.toMutableSet()
                remindList.add(title)
                taskSharedPreferences.edit {
                    putStringSet(SHARED_REMIND_LIST, remindList)
                }
            }
            if (arguments == null) {
                taskViewModel.addTask(task)
            } else {
                task.id = id!!
                taskViewModel.updateTask(task)
            }
            requireContext().showToast(getString(R.string.add_success))
            fragmentManager?.popBackStack()
        }
    }

    companion object {
        private const val TASK_TITLE = "task_title"
        private const val TASK_URL = "task_url"
        private const val TASK_ID = "task_id"

        fun newInstance(id: Long, title: String, url: String): AddTaskFragment =
            AddTaskFragment().apply {
            arguments = bundleOf(
                TASK_ID to id,
                TASK_TITLE to title,
                TASK_URL to url
            )
        }
    }
}