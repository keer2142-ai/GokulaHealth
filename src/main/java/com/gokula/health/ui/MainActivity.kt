package com.gokula.health.ui

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.gokula.health.GokulaHealthApp
import com.gokula.health.R
import com.gokula.health.ai.GeminiHealthAdvisor
import com.gokula.health.data.CattleEntity
import com.gokula.health.data.DailyYield
import com.gokula.health.databinding.ActivityMainBinding
import com.gokula.health.reminder.ReminderScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var scheduler: ReminderScheduler
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
    private var registrationPhotoUri: Uri? = null
    private var photoStatus: TextView? = null
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { captured ->
        photoStatus?.text = if (captured) "Photo saved" else "No photo captured"
        if (!captured) registrationPhotoUri = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as GokulaHealthApp).repository
        viewModel = ViewModelProvider(this, MainViewModel.Factory(repository))[MainViewModel::class.java]
        scheduler = ReminderScheduler(this)
        if (Build.VERSION.SDK_INT >= 33) notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)

        binding.addCattleButton.setOnClickListener { showCattleDialog() }
        binding.addMilkButton.setOnClickListener { selected()?.let { showMilkDialog(it) } }
        binding.addVaccineButton.setOnClickListener { selected()?.let { showVaccinationDialog(it) } }
        binding.addHeatButton.setOnClickListener { selected()?.let { showHeatDialog(it) } }
        binding.aiButton.setOnClickListener { selected()?.let { showAdvisory(it) } }
        binding.passportButton.setOnClickListener { selected()?.let { sharePassport(it) } }

        lifecycleScope.launch {
            viewModel.state.collect { render(it) }
        }
        viewModel.load()
    }

    private fun render(state: MainUiState) {
        binding.totalCattleCard.text = "Cattle\n${state.summary.cattleCount}"
        binding.pendingVaccinesCard.text = "Vaccines\n${state.summary.pendingVaccinations}"
        binding.nearHeatCard.text = "Heat\n${state.summary.nearHeat}"
        binding.herdList.removeAllViews()
        state.cattle.forEach { cattle ->
            binding.herdList.addView(cattleRow(cattle, state.selected?.id == cattle.id))
        }
        val selected = state.selected
        binding.selectedName.text = selected?.let { "${it.name} · ${it.earTagId}" } ?: "Add the first cattle to begin"
        binding.monthlyAverage.text = "Monthly average yield: %.2f L/day".format(state.monthlyAverage)
        val enabled = selected != null
        listOf(binding.addMilkButton, binding.addVaccineButton, binding.addHeatButton, binding.aiButton, binding.passportButton).forEach { it.isEnabled = enabled }
        renderChart(state.chart)
    }

    private fun cattleRow(cattle: CattleEntity, selected: Boolean): View {
        val row = TextView(this)
        val photo = if (cattle.photoUri.isNullOrBlank()) "No photo" else "Photo saved"
        row.text = "${if (selected) "●" else "○"} ${cattle.name}\n${cattle.breed} · ${cattle.gender} · Owner: ${cattle.ownerName} · $photo"
        row.textSize = 16f
        row.setTextColor(getColor(R.color.ink))
        row.setPadding(18, 18, 18, 18)
        row.setBackgroundResource(R.drawable.card_bg)
        row.setOnClickListener { viewModel.select(cattle.id) }
        row.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            topMargin = 10
        }
        return row
    }

    private fun renderChart(points: List<DailyYield>) {
        val entries = points.mapIndexed { index, day -> Entry(index.toFloat(), day.totalLitres.toFloat()) }
        val dataSet = LineDataSet(entries, "30-day milk yield").apply {
            color = getColor(R.color.gokula_green)
            setCircleColor(getColor(R.color.gokula_sky))
            lineWidth = 3f
            circleRadius = 4f
            valueTextSize = 10f
        }
        binding.yieldChart.data = LineData(dataSet)
        binding.yieldChart.axisRight.isEnabled = false
        binding.yieldChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.yieldChart.description.isEnabled = false
        binding.yieldChart.invalidate()
    }

    private fun showCattleDialog() {
        registrationPhotoUri = null
        val fields = listOf("Ear tag ID", "Name", "Breed", "Gender", "Owner").map { hint -> EditText(this).apply { this.hint = hint } }
        val dob = Calendar.getInstance().apply { add(Calendar.YEAR, -3) }
        val layout = verticalForm(fields)
        photoStatus = TextView(this).apply {
            text = "Tap to take cattle photo"
            textSize = 16f
            setPadding(0, 18, 0, 18)
            setOnClickListener { captureRegistrationPhoto() }
        }
        layout.addView(photoStatus)
        addDateRow(layout, "Date of birth: ${dateFormat.format(dob.time)}") { pickDate(dob) }
        AlertDialog.Builder(this)
            .setTitle("Register cattle")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                viewModel.addCattle(fields[0].text.toString(), fields[1].text.toString(), fields[2].text.toString(), dob.timeInMillis, fields[3].text.toString(), fields[4].text.toString(), registrationPhotoUri?.toString())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun captureRegistrationPhoto() {
        val dir = File(filesDir, "cattle_photos").apply { mkdirs() }
        val file = File(dir, "cattle_${System.currentTimeMillis()}.jpg")
        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
        registrationPhotoUri = uri
        cameraLauncher.launch(uri)
    }

    private fun showMilkDialog(cattle: CattleEntity) {
        val litres = EditText(this).apply { hint = "Litres"; inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL }
        val sessions = arrayOf("Morning", "Evening")
        var session = sessions[0]
        AlertDialog.Builder(this)
            .setTitle("Milk entry for ${cattle.name}")
            .setSingleChoiceItems(sessions, 0) { _, which -> session = sessions[which] }
            .setView(litres)
            .setPositiveButton("Save") { _, _ -> viewModel.addMilk(cattle.id, System.currentTimeMillis(), session, litres.text.toString().toDoubleOrNull() ?: 0.0) }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showVaccinationDialog(cattle: CattleEntity) {
        val fields = listOf("Vaccine name", "Notes").map { EditText(this).apply { hint = it } }
        val administered = Calendar.getInstance()
        val due = Calendar.getInstance().apply { add(Calendar.MONTH, 6) }
        val layout = verticalForm(fields)
        addDateRow(layout, "Administered: ${dateFormat.format(administered.time)}") { pickDate(administered) }
        addDateRow(layout, "Next due: ${dateFormat.format(due.time)}") { pickDate(due) }
        AlertDialog.Builder(this)
            .setTitle("Vaccination for ${cattle.name}")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val vaccine = fields[0].text.toString().ifBlank { "FMD" }
                viewModel.addVaccination(cattle.id, vaccine, administered.timeInMillis, due.timeInMillis, fields[1].text.toString()) { dueDate ->
                    scheduler.schedule(dueDate, "Vaccination due", "${cattle.name} needs $vaccine today.")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showHeatDialog(cattle: CattleEntity) {
        val observed = Calendar.getInstance()
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32, 10, 32, 0) }
        addDateRow(layout, "Observed heat: ${dateFormat.format(observed.time)}") { pickDate(observed) }
        AlertDialog.Builder(this)
            .setTitle("Heat cycle for ${cattle.name}")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                viewModel.addHeat(cattle.id, observed.timeInMillis) { reminder ->
                    scheduler.schedule(reminder, "Heat window soon", "${cattle.name} is expected to enter heat in 2 days.")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAdvisory(cattle: CattleEntity) {
        lifecycleScope.launch {
            binding.aiResult.text = "Checking milk trend..."
            val totals = withContext(Dispatchers.IO) { (application as GokulaHealthApp).repository.lastSevenDayTotals(cattle.id) }
            val advice = withContext(Dispatchers.IO) { GeminiHealthAdvisor().requestObservation(totals) }
            binding.aiResult.text = advice
        }
    }

    private fun sharePassport(cattle: CattleEntity) {
        lifecycleScope.launch {
            val repo = (application as GokulaHealthApp).repository
            val text = withContext(Dispatchers.IO) {
                val vaccines = repo.vaccinations(cattle.id).joinToString("\n") { "- ${it.vaccineName}: due ${dateFormat.format(Date(it.nextDueDate))}" }.ifBlank { "- No vaccinations recorded" }
                val heat = repo.heatCycles(cattle.id).firstOrNull()?.let { "Next expected heat: ${dateFormat.format(Date(it.projectedNextDate))}" } ?: "No heat cycle recorded"
                val avg = repo.monthlyAverage(cattle.id)
                "Gokula-Health Passport\n\nName: ${cattle.name}\nEar tag: ${cattle.earTagId}\nBreed: ${cattle.breed}\nGender: ${cattle.gender}\nOwner: ${cattle.ownerName}\nPhoto: ${cattle.photoUri ?: "Not captured"}\n\nMonthly average yield: %.2f L/day\n\nVaccinations:\n%s\n\nBreeding:\n%s".format(avg, vaccines, heat)
            }
            startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Gokula-Health Passport - ${cattle.name}")
                putExtra(Intent.EXTRA_TEXT, text)
            }, "Share health passport"))
        }
    }

    private fun selected(): CattleEntity? = viewModel.state.value.selected

    private fun verticalForm(fields: List<EditText>): LinearLayout =
        LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 10, 32, 0)
            fields.forEach { addView(it) }
        }

    private fun addDateRow(layout: LinearLayout, label: String, onClick: () -> Unit) {
        layout.addView(TextView(this).apply {
            text = label
            textSize = 16f
            setPadding(0, 18, 0, 18)
            setOnClickListener { onClick() }
        })
    }

    private fun pickDate(calendar: Calendar) {
        DatePickerDialog(this, { _, year, month, day ->
            calendar.set(year, month, day, 8, 0, 0)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}
