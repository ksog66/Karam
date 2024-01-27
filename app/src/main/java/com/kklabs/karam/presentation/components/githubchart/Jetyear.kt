package com.kklabs.karam.presentation.components.githubchart

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)

class JetYear private constructor(
    val startDate: LocalDate,
) : JetCalendarType() {
    lateinit var yearMonths: List<JetMonth>

    companion object {
        fun current(
            date: LocalDate = LocalDate.now(),
            firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
        ): JetYear {
            val day: LocalDate = date.with(TemporalAdjusters.firstDayOfYear())
            val last: LocalDate = date.with(TemporalAdjusters.lastDayOfYear())
            val year = JetYear(day)
            year.yearMonths = year.months(firstDayOfWeek)
            return year
        }

        fun monthsBetween(
            startDate: LocalDate,
            endDate: LocalDate,
        ): List<JetMonth> {
            val jetMonths = mutableListOf<JetMonth>()
            var firstDate = startDate
            while (true) {
                jetMonths.add(JetMonth.current(firstDate))
                firstDate = firstDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1)
                if (firstDate > endDate) {
                    break
                }
            }
            return jetMonths
        }
    }

    private fun months(firstDayOfWeek: DayOfWeek): List<JetMonth> {
        val months = mutableListOf<JetMonth>()

        var startDateMonth = this.startDate.withDayOfMonth(1)
        var endDateMonth = this.startDate.withDayOfMonth(this.startDate.lengthOfMonth())

        var currentYear = this.startDate.year
        while (true) {
            months.add(JetMonth.current(startDateMonth, firstDayOfWeek))

            startDateMonth = endDateMonth.plusDays(1)
            endDateMonth = startDateMonth.withDayOfMonth(startDateMonth.lengthOfMonth())
            if (endDateMonth.year > currentYear) {
                break
            }
            currentYear = endDateMonth.year
        }
        return months
    }

    fun year(): String {
        return this.startDate.year.toString()
    }
}