package com.hrhn.presentation.ui.screen.onboarding

import android.Manifest
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hrhn.databinding.FragmentNotificationSettingBinding
import com.hrhn.presentation.ui.screen.MainActivity
import com.hrhn.presentation.util.AlarmManagerUtil
import com.hrhn.presentation.util.Formatter
import com.hrhn.presentation.util.SharedPreferenceManager

class NotificationSettingFragment : Fragment() {
    private var _binding: FragmentNotificationSettingBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val sharedPreferenceManager by lazy { SharedPreferenceManager(requireContext()) }
    private val alarmManager by lazy { AlarmManagerUtil(requireContext()) }

    private var hasNotificationPermission: Boolean = false
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermission = isGranted
        }

    private val timeSetListener by lazy {
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            sharedPreferenceManager.updateAlarmTime(hourOfDay, minute)
            binding.btnTimePicker.text = Formatter.getTimeString(hourOfDay, minute)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
        binding.btnTimePicker.text = with(sharedPreferenceManager) {
            Formatter.getTimeString(
                getNotificationHour(),
                getNotificationMinute()
            )
        }
        binding.btnTimePicker.setOnClickListener {
            showTimePicker()
        }
        binding.btnSetAlarm.setOnClickListener {
            if (hasNotificationPermission) {
                alarmManager.setRepeatAlarm(sharedPreferenceManager.getAlarmTime())
                sharedPreferenceManager.updateNotificationOnOff(true)
                close()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
        binding.btnNoAlarm.setOnClickListener { close() }
    }

    private fun showTimePicker() {
        TimePickerDialog(
            context,
            timeSetListener,
            sharedPreferenceManager.getNotificationHour(),
            sharedPreferenceManager.getNotificationMinute(),
            DateFormat.is24HourFormat(context)
        ).show()
    }

    private fun close() {
        sharedPreferenceManager.updateOnboardingState(true)
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}