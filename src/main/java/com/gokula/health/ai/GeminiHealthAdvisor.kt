package com.gokula.health.ai

import com.gokula.health.BuildConfig
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt

class GeminiHealthAdvisor {
    fun localObservation(values: List<Double>): String {
        if (values.size < 4) return "Add at least four days of milk entries to see a useful health observation."
        val firstHalf = values.take(values.size / 2).average()
        val secondHalf = values.takeLast(values.size - values.size / 2).average()
        if (firstHalf <= 0.0) return "Milk trend is too small to compare yet."
        val drop = ((firstHalf - secondHalf) / firstHalf * 100).roundToInt()
        return when {
            drop >= 20 -> "Yield has dropped $drop%. Check feed, hydration, udder swelling, fever, and consider a veterinary visit."
            drop >= 10 -> "Yield is down $drop%. Watch the animal closely and compare with heat-cycle or vaccination dates."
            else -> "Yield is stable. Continue regular milk logging and scheduled vaccination checks."
        }
    }

    fun requestObservation(values: List<Double>): String {
        if (BuildConfig.GEMINI_API_KEY.isBlank()) return localObservation(values)
        return try {
            val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${BuildConfig.GEMINI_API_KEY}"
            val body = """{"contents":[{"parts":[{"text":"Give a short cattle health observation from these last 7 daily milk totals in litres. Do not diagnose. Values: $values"}]}]}"""
            val connection = URL(endpoint).openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.outputStream.use { it.write(body.toByteArray()) }
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            Regex(""""text"\s*:\s*"([^"]+)"""").find(response)?.groupValues?.get(1)?.replace("\\n", "\n")
                ?: localObservation(values)
        } catch (_: Exception) {
            localObservation(values)
        }
    }
}
